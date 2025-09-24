#!/bin/bash

# Script to launch spring-security-webmvc-pac4j-boot-demo and verify it works
# Usage: ./run_and_check_webmvc.sh

set -e  # Stop script on error

echo "🚀 Starting spring-security-webmvc-pac4j-boot-demo..."

# Go to project directory (one level up from ci/)
cd ..

# Clean and compile project (package jar)
echo "📦 Compiling project..."
/Users/bidou/tools/apache-maven-3.9.0/bin/mvn -f /Users/bidou/pac4jecosystem/spring-security-webmvc-pac4j-boot-demo/pom.xml clean package -q

# Ensure target directory exists
mkdir -p target

# Start Spring Boot jar in background
JAR="target/spring-security-webmvc-pac4j-boot-demo.jar"
if [ ! -f "$JAR" ]; then
  # In case the finalName differs, try to find the produced boot jar
  JAR=$(ls -1 target/*-SNAPSHOT.jar 2>/dev/null | head -n1)
  if [ -z "$JAR" ]; then
    JAR=$(ls -1 target/*.jar 2>/dev/null | head -n1)
  fi
fi

if [ ! -f "$JAR" ]; then
  echo "❌ Could not locate a built jar in target/." >&2
  ls -la target || true
  exit 1
fi

echo "🌐 Starting Spring Boot application ($JAR)..."
java -jar "$JAR" > target/app.log 2>&1 &
APP_PID=$!

# Wait for server to start (maximum 60 seconds)
echo "⏳ Waiting for server startup..."
for i in {1..60}; do
    if curl -s -o /dev/null -w "%{http_code}" http://localhost:8080 | grep -q "200"; then
        echo "✅ Server started successfully!"
        break
    fi
    if [ $i -eq 60 ]; then
        echo "❌ Timeout: Server did not start within 60 seconds"
        echo "📋 Server logs:"
        cat target/app.log || true
        kill $APP_PID 2>/dev/null || true
        exit 1
    fi
    sleep 1
done

# Verify application responds correctly
echo "🔍 Verifying HTTP response..."
HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080)

if [ "$HTTP_CODE" = "200" ]; then
    echo "✅ Application responds with HTTP 200"
    echo "🌐 Application accessible at: http://localhost:8080"

    # Test basic authentication
    echo "🔐 Testing basic authentication..."
    
    # Test login page
    echo "📄 Testing login page..."
    LOGIN_RESPONSE=$(curl -s -w "HTTP_CODE:%{http_code}" http://localhost:8080/login.html)
    LOGIN_HTTP_CODE=$(echo "$LOGIN_RESPONSE" | grep "HTTP_CODE:" | cut -d: -f2)
    
    if [ "$LOGIN_HTTP_CODE" = "200" ]; then
        echo "✅ Login page accessible (HTTP 200)"
        
        # Test protected page without authentication
        echo "🔒 Testing protected page without authentication..."
        PROTECTED_RESPONSE=$(curl -s -w "HTTP_CODE:%{http_code}" http://localhost:8080/protected/index.html)
        PROTECTED_HTTP_CODE=$(echo "$PROTECTED_RESPONSE" | grep "HTTP_CODE:" | cut -d: -f2)
        
        if [ "$PROTECTED_HTTP_CODE" = "401" ] || [ "$PROTECTED_HTTP_CODE" = "403" ]; then
            echo "✅ Protected page correctly requires authentication (HTTP $PROTECTED_HTTP_CODE)"
            
            # Test authentication with valid credentials
            echo "🔑 Testing authentication with valid credentials..."
            AUTH_RESPONSE=$(curl -s -u "user:user" -w "HTTP_CODE:%{http_code}" http://localhost:8080/protected/index.html)
            AUTH_HTTP_CODE=$(echo "$AUTH_RESPONSE" | grep "HTTP_CODE:" | cut -d: -f2)
            
            if [ "$AUTH_HTTP_CODE" = "200" ]; then
                echo "✅ Authentication successful with valid credentials (HTTP 200)"
                AUTH_TEST_PASSED=true
            else
                echo "❌ Authentication failed with valid credentials (HTTP $AUTH_HTTP_CODE)"
                AUTH_TEST_PASSED=false
            fi
        else
            echo "❌ Protected page should require authentication (HTTP $PROTECTED_HTTP_CODE)"
            AUTH_TEST_PASSED=false
        fi
    else
        echo "❌ Login page not accessible (HTTP $LOGIN_HTTP_CODE)"
        AUTH_TEST_PASSED=false
    fi
else
    echo "❌ Initial test failed! HTTP code received: $HTTP_CODE"
    echo "📋 Server logs:"
    cat target/app.log || true
    AUTH_TEST_PASSED=false
fi

# Always stop the server
echo "🛑 Stopping server..."
kill $APP_PID 2>/dev/null || true

# Wait a moment for graceful shutdown
sleep 2

# Force kill if still running
kill -9 $APP_PID 2>/dev/null || true

if [ "$HTTP_CODE" = "200" ] && [ "$AUTH_TEST_PASSED" = "true" ]; then
    echo "🎉 spring-security-webmvc-pac4j-boot-demo test completed successfully!"
    echo "✅ All tests passed:"
    echo "   - Application responds with HTTP 200"
    echo "   - Login page is accessible"
    echo "   - Protected pages require authentication"
    echo "   - Authentication works with valid credentials"
    exit 0
else
    echo "💥 spring-security-webmvc-pac4j-boot-demo test failed!"
    if [ "$HTTP_CODE" != "200" ]; then
        echo "❌ Application HTTP test failed (code: $HTTP_CODE)"
    fi
    if [ "$AUTH_TEST_PASSED" != "true" ]; then
        echo "❌ Authentication test failed"
    fi
    exit 1
fi

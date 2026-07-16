#!/bin/bash

# Build & run script for SynapTix Broadband (Standard Maven Layout)

COMMAND=$1

clean() {
    echo "Cleaning build directory..."
    rm -rf build
    rm -rf dist
}

compile() {
    echo "Compiling Java source files..."
    mkdir -p build/classes
    javac -Xlint:none -cp .:flatlaf-3.1.1.jar:postgresql-42.6.0.jar:rs2xml.jar:itext-2.1.7.jar:HikariCP-4.0.3.jar:slf4j-api-1.7.30.jar:slf4j-simple-1.7.30.jar:jfreechart-1.5.3.jar:javax.mail-1.6.2.jar:activation-1.1.1.jar -d build/classes src/main/java/com/synaptix/isp/*.java
    if [ $? -eq 0 ]; then
        echo "Compilation successful!"
        echo "Copying resources..."
        # Copy image assets and properties from resources to build output
        cp src/main/resources/*.png build/classes/ 2>/dev/null || true
        cp src/main/resources/*.properties build/classes/ 2>/dev/null || true
    else
        echo "Compilation failed!"
        exit 1
    fi
}

package() {
    compile
    echo "Packaging executable JAR..."
    mkdir -p dist
    mkdir -p build
    # Create manifest file temporarily
    echo "Main-Class: com.synaptix.isp.Home" > build/manifest.mf
    echo "Class-Path: ../flatlaf-3.1.1.jar ../postgresql-42.6.0.jar ../rs2xml.jar ../itext-2.1.7.jar ../HikariCP-4.0.3.jar ../slf4j-api-1.7.30.jar ../slf4j-simple-1.7.30.jar ../jfreechart-1.5.3.jar ../javax.mail-1.6.2.jar ../activation-1.1.1.jar" >> build/manifest.mf
    
    jar cfm dist/ISP.jar build/manifest.mf -C build/classes .
    echo "JAR package created at dist/ISP.jar"
}

run() {
    compile
    echo "Launching SynapTix Broadband ISP Management System..."
    java -cp build/classes:flatlaf-3.1.1.jar:postgresql-42.6.0.jar:rs2xml.jar:itext-2.1.7.jar:HikariCP-4.0.3.jar:slf4j-api-1.7.30.jar:slf4j-simple-1.7.30.jar:jfreechart-1.5.3.jar:javax.mail-1.6.2.jar:activation-1.1.1.jar com.synaptix.isp.Home
}

case "$COMMAND" in
    clean)
        clean
        ;;
    compile)
        compile
        ;;
    package)
        package
        ;;
    run)
        run
        ;;
    *)
        echo "Usage: ./build.sh {clean|compile|package|run}"
        exit 1
        ;;
esac

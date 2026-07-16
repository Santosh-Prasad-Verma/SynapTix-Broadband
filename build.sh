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
    javac -Xlint:none -cp .:flatlaf-3.1.1.jar:postgresql-42.6.0.jar:rs2xml.jar:itext-2.1.7.jar:HikariCP-4.0.3.jar:slf4j-api-1.7.30.jar:logback-classic-1.2.11.jar:logback-core-1.2.11.jar:flyway-core-8.5.13.jar:jfreechart-1.5.3.jar:javax.mail-1.6.2.jar:activation-1.1.1.jar -d build/classes src/main/java/com/synaptix/isp/*.java
    if [ $? -eq 0 ]; then
        echo "Compilation successful!"
        echo "Copying resources..."
        # Copy all images, configuration files, XMLs, and SQL files recursively
        cp -r src/main/resources/* build/classes/ 2>/dev/null || true
    else
        echo "Compilation failed!"
        exit 1
    fi
}

package() {
    compile
    echo "Extracting dependency libraries for self-contained JAR..."
    mkdir -p build/deps
    for f in *.jar; do
        if [ -f "$f" ]; then
            echo "Extracting $f..."
            (cd build/deps && jar xf ../../"$f")
        fi
    done
    
    # Remove META-INF signature and manifest files to prevent security/digest validation errors
    rm -rf build/deps/META-INF/*.SF build/deps/META-INF/*.DSA build/deps/META-INF/*.RSA build/deps/META-INF/MANIFEST.MF 2>/dev/null || true
    
    # Remove module-info.class to prevent future javac modular compilation issues
    rm -f build/deps/module-info.class 2>/dev/null || true
    
    # Copy all extracted dependencies into compile output directory
    cp -r build/deps/* build/classes/ 2>/dev/null || true
    
    echo "Packaging self-contained executable JAR..."
    mkdir -p dist
    # Create manifest file temporarily
    echo "Main-Class: com.synaptix.isp.Home" > build/manifest.mf
    
    jar cfm dist/ISP.jar build/manifest.mf -C build/classes .
    echo "JAR package created successfully at dist/ISP.jar (Self-contained executable)"
}

run() {
    compile
    echo "Launching SynapTix Broadband ISP Management System..."
    java -cp build/classes:flatlaf-3.1.1.jar:postgresql-42.6.0.jar:rs2xml.jar:itext-2.1.7.jar:HikariCP-4.0.3.jar:slf4j-api-1.7.30.jar:logback-classic-1.2.11.jar:logback-core-1.2.11.jar:flyway-core-8.5.13.jar:jfreechart-1.5.3.jar:javax.mail-1.6.2.jar:activation-1.1.1.jar com.synaptix.isp.Home
}

test_suite() {
    echo "Running automated test suites..."
    if command -v mvn &> /dev/null; then
        echo "Maven found on system. Executing test phase..."
        mvn test
    else
        echo "Maven not found on system path."
        echo "Note: If you are running inside NetBeans, IntelliJ, or Eclipse, you can right-click the project and select 'Test' or 'Run Unit Tests' to execute the test suite automatically."
        echo "Alternatively, install maven (e.g. 'sudo dnf install maven' or 'sudo apt install maven') to run tests on the command line via 'mvn test'."
    fi
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
    test)
        test_suite
        ;;
    *)
        echo "Usage: ./build.sh {clean|compile|package|run|test}"
        exit 1
        ;;
esac

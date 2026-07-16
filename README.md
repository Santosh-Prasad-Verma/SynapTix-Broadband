<p align="center">
  <img src="./icons/SynapTix-Broadband-logo-for-black-screen.png" width="250" height="250" alt="SynapTix Broadband Logo">
</p>

# 🌐 SynapTix Broadband — ISP Management System

[![Java Version](https://img.shields.io/badge/Java-11%2B-blue?logo=java&logoColor=white&style=for-the-badge)](https://www.oracle.com/java/)
[![Database](https://img.shields.io/badge/Database-PostgreSQL%2015-blue?logo=postgresql&logoColor=white&style=for-the-badge)](https://www.postgresql.org/)
[![UI Style](https://img.shields.io/badge/UI%20Look-FlatLaf%20Dark-purple?logo=fluentui&logoColor=white&style=for-the-badge)](https://github.com/JFormDesigner/FlatLaf)
[![Build Tool](https://img.shields.io/badge/Build%20Tool-Maven-orange?logo=apachemaven&logoColor=white&style=for-the-badge)](https://maven.apache.org/)

A premium, enterprise-grade desktop application built in Java Swing for Internet Service Providers (ISPs) to orchestrate customers, subscription plans, employee rosters, complaints support tickets, visual analytics, and database utilities.

---

## ✨ Features Spotlight

### 📊 Dashboard & Visual Analytics
* **Interactive Statistics:** Highlights active subscribers, monthly recurring revenue, and open complaints ratio.
* **Plan Distribution:** Real-time JFreeChart pie chart representing subscriber ratios per broadband plan.
* **NOC Live Bandwidth Wave Monitor:** Dynamic dynamic chart displaying simulated live download/upload throughput and latency (Ping).

### 📧 Automated Billing & Messaging
* **PDF Invoice Compiler:** Instantly generates a clean, formatted receipt PDF using iText with dynamic 18% CGST/SGST calculations.
* **Automated Email Billing:** Pre-fills customer email addresses and sends PDF invoices directly to customer inboxes in background threads.
* **Ticket Resolution Alerts:** Auto-prompts support admins to send email confirmations when support tickets are resolved.
* **Bulk Broadcast:** Sends customized mass email alerts to all customer addresses simultaneously in background threads.

### 📥 Bulk CSV Import & Export Utilities
* **Data Importers:** Bulk inserts or updates Customers, Employees, and Plans using CSV files.
* **Upsert Capability:** Utilizes `ON CONFLICT (ID) DO UPDATE` queries so that existing IDs update in-place without constraint issues.
* **Financial CSV Exporter:** Computes and exports monthly revenue details, base plans, taxes, and customer names to clean CSV files.

### 📁 Database Utilities & Resiliency
* **One-Click Backup:** Runs `pg_dump` in background threads to let administrators save a full snapshot `.sql` of the database state.
* **One-Click Restore:** Runs `psql` to import database snapshots and restore state immediately.
* **High-Performance Pooling:** Incorporates **HikariCP** connection pooling to manage active PostgreSQL client sockets.

---

## 🛠️ Tech Stack & Dependencies
* **Core:** Java SE (Swing, AWT)
* **Design System:** FlatLaf (Modern, responsive look-and-feel)
* **Database:** PostgreSQL (with `postgresql-42.6.0.jar` driver)
* **Connection Pooling:** HikariCP + SLF4J logger API
* **Charts Engine:** JFreeChart 1.5.3
* **PDF Compiler:** iText 2.1.7
* **Mailing Engine:** javax.mail API

---

## 🚀 Installation & Setup

### Step 1: Spin up PostgreSQL Database (Docker Compose)
Start the PostgreSQL container, which automatically initializes database schemas and mock records on port **`5435`**:
```bash
docker compose up -d
```

---

### Step 2: Configure SMTP and Database Properties
Open [src/main/resources/db.properties](file:///home/tarun/Videos/ISP-Java/src/main/resources/db.properties) and populate the database connections and your Gmail App credentials:
```properties
db.url=jdbc:postgresql://localhost:5435/isp
db.username=postgres
db.password=postgres

# Mail Settings (Optional real SMTP integration; fallback prints message to console log)
mail.smtp.host=smtp.gmail.com
mail.smtp.port=587
mail.smtp.auth=true
mail.smtp.starttls.enable=true
mail.smtp.username=your-gmail-id@gmail.com
mail.smtp.password=your-16-digit-app-password
```
> ⚠️ **Note:** To send real emails, you must generate a 16-character **App Password** from your Google account settings. Normal passwords will be rejected by Google's SMTP servers.

---

### Step 3: Build & Launch
Use the helper script `build.sh` to compile, package, test, and execute the app:

* **Compile source files & copy resources:**
  ```bash
  ./build.sh compile
  ```
* **Run the automated unit tests:**
  ```bash
  ./build.sh test
  ```
* **Compile and run the GUI:**
  ```bash
  ./build.sh run
  ```
* **Package into a self-contained executable fat JAR:**
  ```bash
  ./build.sh package
  ```
  *(Produces output runnable fat jar inside `dist/ISP.jar`)*

---

## 🔑 Administrative Account
When launching the application, log in using the default credentials:
* **Username:** `admin`
* **Password:** `admin`

*(Upon your first successful login, the system automatically upgrades the password database from legacy plaintext to secure PBKDF2 hashes).*

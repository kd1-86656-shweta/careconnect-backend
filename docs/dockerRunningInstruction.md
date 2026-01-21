# 🐳 Docker MySQL – Permanent Running Instructions (CareConnect)

This document explains **how to run MySQL in Docker safely**, so that:

* ✅ Data is **never lost**
* ✅ You don’t debug connection issues again
* ✅ Spring Boot connects reliably every time

---

## 🔑 Core Concept (Must Understand Once)

* **Container** → Temporary (can be stopped/deleted)
* **Volume** → Permanent (stores actual database data)

👉 **As long as the volume exists, your data is SAFE.**

---

## 🧱 ONE-TIME SETUP (DO THIS ONLY ONCE)

### 1️⃣ Create a Docker Volume (Permanent Storage)

```powershell
docker volume create mysql-careconnect-data
```

This volume:

* Stores all MySQL data
* Survives container stop/start
* Survives system reboot

---

### 2️⃣ Run MySQL Container (USE THIS COMMAND ALWAYS)

```powershell
docker run -d `
  --name mysql-careconnect `
  -p 3307:3306 `
  -v mysql-careconnect-data:/var/lib/mysql `
  -e "MYSQL_ROOT_PASSWORD=Password" `
  -e MYSQL_DATABASE=careconnect `
  mysql:8.0
```

✅ What this does:

* Uses fixed container name
* Uses fixed port (3307)
* Stores DB in permanent volume
* Creates database automatically

---

## ▶️ DAILY USAGE (VERY SIMPLE)

### Start MySQL (when system boots / before work)

```powershell
docker start mysql-careconnect
```

### Stop MySQL (after work)

```powershell
docker stop mysql-careconnect
```

❗ Do **NOT** use `docker run` again if container already exists.

---

## 🔌 SPRING BOOT CONFIGURATION (FIXED FOREVER)

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/careconnect
spring.datasource.username=root
spring.datasource.password=Password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

⚠️ Important:

* `#` is escaped as `\#`
* Do not change DB name unless intentional

---

## 🛡️ DATA SAFETY GUARANTEE

| Action           | Data Safe?                |
| ---------------- | ------------------------- |
| docker stop      | ✅ YES                     |
| docker start     | ✅ YES                     |
| System restart   | ✅ YES                     |
| Container delete | ✅ YES (volume keeps data) |
| Docker restart   | ✅ YES                     |
| Volume delete    | ❌ NO (data lost)          |

👉 **Only deleting the volume deletes data.**

---

## 🔍 VERIFY SETUP

### Check container status

```powershell
docker ps
```

### Check volume exists

```powershell
docker volume ls
```

### Login to MySQL

```powershell
mysql -h 127.0.0.1 -P 3307 -u root -p
```

---

## 🚀 OPTIONAL (RECOMMENDED FOR COMFORT)

### Auto-start MySQL with system

```powershell
docker update --restart unless-stopped mysql-careconnect
```

Now MySQL:

* Starts automatically
* Needs zero manual effort

---

## 🧠 FINAL REMINDER

> **Never fear losing data again.**
> As long as the volume exists, your database is permanent.

---

📌 **File to keep forever:** `dockerRunningInstruction.md`

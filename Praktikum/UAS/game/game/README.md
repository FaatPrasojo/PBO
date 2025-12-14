# 🎮 RPG Battle Simulator - Complete Setup Guide

## ✅ Status: READY TO USE!

Aplikasi sudah selesai dibuat dan siap dijalankan.

---

## 🚀 QUICK START

### ⚠️ WinRAR Issue on Windows
Windows mungkin treat `.jar` sebagai ZIP file (WinRAR overclaim). Ada 3 solusi:

---

### ✅ **SOLUSI 1: Gunakan Shortcut (RECOMMENDED - 100% Work)**
Paling simple dan tidak butuh registry edit!
```
Double-click: BattleSimulator.lnk
```
Selesai! Shortcut ini tidak bisa di-override oleh WinRAR.

---

### ✅ **SOLUSI 2: Gunakan Batch Launcher**
```
Double-click: RUN-GAME.bat
```
Juga work 100%, tapi akan ada black console window.

---

### ✅ **SOLUSI 3: Force Java via Registry (Nuclear Option)**
Jika WinRAR terus ambil alih:

1. **Right-click**: `NUKE-WINRAR.reg`
2. Pilih: **Merge**
3. Klik: **Yes** pada dialog confirmation
4. **Restart** computer
5. Sekarang double-click `.jar` akan work

**⚠️ Warning:** Solusi ini delete semua .jar associations lalu re-create dari nol. Perlu Administrator privilege.

---

### 🎮 Pilihan Terbaik (Ranking):
1. **BattleSimulator.lnk** ← PALING RECOMMENDED (shortcut)
2. **RUN-GAME.bat** ← Alternative yang simple
3. **JAR + NUKE-WINRAR.reg** ← Jika ingin double-click `.jar` file langsung

---

## 📦 File Penting

| File | Tujuan |
|------|--------|
| **`BattleSimulator.lnk`** | ⭐⭐⭐ **MAIN LAUNCHER** - Shortcut (100% work, tidak bisa di-override WinRAR) |
| `RUN-GAME.bat` | Alternative launcher (.bat file) |
| `BattleSimulator.jar` | Executable JAR file (hasil build Ant) |
| `NUKE-WINRAR.reg` | Nuclear option untuk force Java (jika shortcut tidak work) |
| `force-java-jar.reg` | Gentle registry fix untuk .jar association |
| `fix-jar-association.reg` | Alternative registry fix |
| `build.xml` | Apache Ant build configuration |
| `antbuild.bat` | Ant-style batch builder untuk rebuild |
| `src/` | Source code directory (26 Java files) |
| `lib/` | External dependencies (SQLite JDBC) |
| `build/` | Compiled files (auto-generated) |
| `dist/` | Distribution folder dengan JAR final |
| `README.md` | Documentation |

---

## 🎯 Features Lengkap

✅ **Hero Management**
- Add/Edit/Delete heroes
- Assign multiple skills ke hero
- Auto-save ke database

✅ **Enemy Management**
- Add/Edit/Delete enemies
- Boss monsters dan regular monsters
- Level/threat system

✅ **Skill System**
- HealSkill (restore HP)
- PiercingStrike (multiplier damage)
- Multiple skills per character

✅ **Battle Simulator**
- Real-time turn-based combat
- Random enemy targeting
- Status effects (Regen, Shield)
- Auto-save battle results

✅ **Battle History**
- View semua battle yang pernah dilakukan
- Filter by winner (Heroes/Enemies/Draw)
- Lihat detail log setiap battle
- Delete individual atau clear all

✅ **Data Persistence**
- Heroes auto-save ke database
- Enemies auto-save ke database
- Battle history tercatat
- Data persist setelah aplikasi ditutup

---

## 🔧 Rebuild JAR dengan Ant

```batch
cd d:\Project\game
antbuild.bat build
```

Atau untuk clean + rebuild:
```batch
antbuild.bat rebuild
```

**Build Targets:**
- `antbuild.bat compile` - Hanya compile Java files
- `antbuild.bat build` - Compile + buat JAR (default)
- `antbuild.bat rebuild` - Clean + compile + JAR
- `antbuild.bat run` - Build + jalankan
- `antbuild.bat clean` - Hapus compiled files

---

## 🐛 Troubleshooting

### ❌ JAR File Selalu Masuk WinRAR
**Solusi Cepat:**
- Double-click: `BattleSimulator.lnk` (shortcut, 100% work!)

**Solusi Alternatif:**
- Double-click: `RUN-GAME.bat` (batch file)

**Solusi Registry (Nuclear):**
1. Right-click: `NUKE-WINRAR.reg` → **Merge**
2. Klik: **Yes** (butuh Administrator)
3. Restart computer
4. Sekarang `.jar` akan open dengan Java

### ❌ Java Binary Tidak Muncul di "Open With"
Ini karena WinRAR aggressive override. Gunakan shortcut/batch file instead:
- `BattleSimulator.lnk` atau `RUN-GAME.bat`

Atau fix registry dengan `NUKE-WINRAR.reg` (perlu Administrator).

### Error: "Unable to access jarfile"
- Pastikan sudah run: `antbuild.bat rebuild`
- Atau coba: `BattleSimulator.lnk` atau `RUN-GAME.bat`

### Error: "No suitable driver found"
- SQLite driver sudah di-embed di JAR
- Jika error, pastikan menggunakan file dari `dist/` folder

### GUI tidak muncul
- Tunggu 2-3 detik (first load lambat)
- Cek console untuk error message
- Pastikan Java v11+: `java -version`

---

## 📁 Folder Structure (Ant Convention)

```
d:\Project\game\
├── 📁 src/                           ← Source code (26 Java files)
│   ├── BattleGameGUI.java            ← Main application entry point
│   ├── BattlePanel.java              ← Battle simulator UI
│   ├── HeroPanel.java                ← Hero management
│   ├── EnemyPanel.java               ← Enemy management
│   ├── SkillPanel.java               ← Skill management
│   ├── HistoryPanel.java             ← Battle history viewer
│   ├── DatabaseManager.java          ← Database CRUD operations
│   ├── BattleHistory.java            ← Battle data model
│   └── [18 more Java files...]
│
├── 📁 lib/                           ← External dependencies
│   └── sqlite-jdbc-3.51.1.0.jar      ← SQLite JDBC driver
│
├── 📁 build/                         ← Compiled files (auto-generated)
│   ├── *.class                       ← Compiled Java classes
│   ├── org/                          ← SQLite driver classes
│   └── META-INF/                     ← Java manifest
│
├── 📁 dist/                          ← Distribution folder
│   └── BattleSimulator.jar           ← Executable JAR (final product)
│
├── 🎮 BattleSimulator.jar            ← Copy of dist/BattleSimulator.jar
├── 💾 battle_history.db              ← SQLite database (auto-created)
│
├── 📝 build.xml                      ← Apache Ant build file
├── 🔧 antbuild.bat                   ← Ant-style batch builder
├── 📋 run.bat                        ← Quick launcher
├── 📋 compile.bat                    ← Compiler-only script
│
├── 🔗 fix-jar-association.reg        ← Fix JAR double-click issue
├── 📖 README.md                      ← This file
└── [Other files]
```

---

## 💡 Tips & Tricks

### Multi-Select Skills
Saat membuat hero, bisa select **2 atau lebih skills** dengan:
- **Click** - Select satu
- **Ctrl+Click** - Select multiple
- **Shift+Click** - Select range

### Battle History
- Filter by winner untuk lihat hero wins/enemy wins
- Click row untuk lihat full battle log
- Delete individual battle atau clear all

### Database
Untuk inspect database:
```cmd
java -cp .;sqlite-jdbc-3.51.1.0.jar DatabaseInspector
```

---

## 📊 System Requirements

- **Java**: 11 atau lebih baru
- **RAM**: 512 MB minimum
- **Disk**: 30 MB
- **OS**: Windows, Mac, Linux

---

## 🔗 Resources

- Java Download: https://www.java.com
- SQLite JDBC: https://github.com/xerial/sqlite-jdbc
- DB Browser: https://sqlitebrowser.org/

---

## 📝 Version Info

**Version:** 1.0
**Last Updated:** December 12, 2025
**Status:** ✅ Production Ready

---

**Enjoy the game!** 🎮🎉

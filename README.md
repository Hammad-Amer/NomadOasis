# NomadOasis

> A JavaFX desktop application for booking travel packages, hotels, and trekking gear across Pakistan's northern areas.

NomadOasis is a role-based travel & tourism management system built with **JavaFX** and **MySQL**. Travelers can explore destinations, buy tour packages, book hotel rooms, shop for trekking gear, and raise queries; consultants answer those queries; and admins manage the catalog of hotels, packages, and store items.

## Features

### Traveler
- **Sign up / log in** with validation (18+ age check, 13-digit CNIC, unique username)
- **Explore destinations** — Chitral, Fairy Meadows, Hunza Valley, Skardu, Swat Valley, and Wadi Shogran
- **Browse & buy tour packages** with destination, duration, description, and pricing
- **Book & cancel hotel rooms** with live room availability
- **Trekking gear shop** with a shopping cart and stock management
- **Ask a consultant** — submit a query that is auto-assigned to a consultant, and read the response
- **Edit profile** and view a full **activity/booking history log**

### Consultant
- View queries assigned to you and **respond** to travelers

### Admin
- **Add / remove hotels and rooms**
- **Add / remove tour packages**
- **Add store items and restock** existing inventory

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 22 |
| UI | JavaFX (FXML layouts + CSS styling) |
| Database | MySQL |
| DB Driver | MySQL Connector/J 9.0.0 (JDBC) |
| IDE / Build | Eclipse with the e(fx)clipse plugin |

## Architecture

- **`user/`** — application entry point (`Main`) and the login/signup screen that routes each user to the correct dashboard based on their role.
- **`traveler/`, `admin/`, `consultant/`** — the FXML views, CSS, and controllers for each role's dashboard.
- **`backend/`** — domain models (`Traveler`, `Admin`, `Consultant`, `Hotel`, `Room`, `Package`, `Item`, `Cart`, `Query`, `Booking`) and the data layer.

Design patterns used:
- **Singleton** — `DBHandler` (single shared JDBC connection) and `SharedState` (holds the logged-in user across screens).
- **Factory** — `UserFactory` creates the correct `User` subtype from the stored `userType`.

## Getting Started

### Prerequisites
- **JDK 22**
- **JavaFX SDK** (configured as an Eclipse user library — the project references one named `javaFXN`)
- **MySQL Server** running locally
- **MySQL Connector/J 9.0.0** (`mysql-connector-j-9.0.0.jar`) on the classpath

### 1. Set up the database
Create the schema and tables from the included SQL script:

```bash
mysql -u root -p < DB_NomadOasis.sql
```

This creates the `NOMADOASIS` database with all required tables (`User1`, `Package`, `Hotel`, `Room`, `Item`, `Cart`, `CartItems`, `BookingRoom`, `BookingPackage`, `query1`, `logs1`).

### 2. Configure database credentials
Database settings are read from a local `db.properties` file that is **not** committed (it can contain secrets). Copy the template and fill in your own values:

```bash
cp src/db.properties.example src/db.properties
```

```properties
db.url=jdbc:mysql://localhost:3306/NOMADOASIS
db.username=root
db.password=your_password_here
```

### 3. Open and run in Eclipse
1. Import the project into Eclipse (with the e(fx)clipse plugin installed).
2. Ensure the JavaFX user library and the MySQL Connector/J jar are on the module path.
3. Run **`user.Main`** as the main class.

The app opens on the login/signup screen. Register a traveler account, or seed an Admin/Consultant directly in the `User1` table to access those dashboards.

## Project Structure

```
NomadOasis/
├── src/
│   ├── user/         # Main entry point + login/signup + role routing
│   ├── traveler/     # Traveler dashboard: destinations, packages, hotels, shop, history
│   ├── admin/        # Admin dashboard: manage hotels, packages, items
│   ├── consultant/   # Consultant dashboard: respond to queries
│   └── backend/      # Domain models + DBHandler (JDBC), UserFactory, SharedState
├── IMGs/             # UI images and assets
├── DB_NomadOasis.sql # Database schema
├── .classpath        # Eclipse project configuration
├── .project
└── README.md
```

## Notes

This project was built as a university coursework submission and is intended as a learning / portfolio piece. Passwords are stored in plaintext and the DB connection targets a local MySQL instance — it is **not** hardened for production use.

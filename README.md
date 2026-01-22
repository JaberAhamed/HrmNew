# 📘 Software Requirement Specification (SRS)

## HRM Mobile Application (Kotlin Multiplatform)

---

## 1. Purpose

The purpose of this document is to define the **UI screens**, **navigation flow**, and **functional scope** of the HRM Mobile Application built using **Kotlin Multiplatform (KMP)**.

The initial goal of this SRS is **UI and navigation generation only**. Backend logic, APIs, and advanced business rules are intentionally excluded.

---

## 2. Application Scope

The application includes **only** the following screens and modules:

* Splash Screen
* Landing Screen
* Login Screen
* Dashboard
* Attendance Tracking
* Leave Management
* Task Management
* Client Visits
* Announcements

> ⚠️ No additional features or modules are included in this phase.

---

## 3. Target Platforms

* **Android** (Kotlin Multiplatform)
* **iOS** (Kotlin Multiplatform)

Shared UI components and navigation logic will be implemented using **Kotlin Multiplatform**.

---

## 4. Screen & Functional Requirements

### 4.1 Splash Screen

**Description:**
The Splash Screen is the first screen displayed when the application launches.

**UI Requirements:**

* Display application logo
* Display application branding

**Navigation:**

* Automatically navigates to the **Landing Screen** after loading

---

### 4.2 Landing Screen

**Description:**
Acts as the entry point before authentication.

**UI Requirements:**

* App introduction text
* Login button

**Navigation:**

* Login button → **Login Screen**

---

### 4.3 Login Screen

**Description:**
Allows users to authenticate into the application.

**UI Requirements:**

* Input field for **Email / Employee ID**
* Input field for **Password**
* Login button

**Navigation:**

* Successful login → **Dashboard**

---

### 4.4 Dashboard (Modern)

**Description:**
Provides an overview of employee information and quick access to core modules.

**UI Requirements:**

* Real-time attendance status display
* Punch In / Punch Out button
* Quick statistics section

**Navigation:**

* Attendance Tracking
* Leave Management
* Task Management
* Client Visits
* Announcements

---

### 4.5 Attendance Tracking

**Description:**
Manages employee attendance activities.

**UI Requirements:**

* Punch In button
* Punch Out button
* GPS-based attendance status display
* Attendance history list

**Behavior:**

* Support offline punch indication in UI

---

### 4.6 Leave Management

**Description:**
Manages employee leave information.

**UI Requirements:**

* Leave balance display
* Apply leave form
* Leave history list

---

### 4.7 Task Management

**Description:**
Manages tasks assigned to employees.

**UI Requirements:**

* Task list
* Task priority indicator
* Task status indicator

---

### 4.8 Client Visits

**Description:**
Logs and displays client visit information.

**UI Requirements:**

* Add client visit button
* Capture photo
* Upload documents
* Display GPS location
* Client visit list

---

### 4.9 Announcements

**Description:**
Displays company announcements.

**UI Requirements:**

* Announcement list
* Read / Unread indicator
* Announcement details view

---

## 5. Navigation Flow

```
Splash Screen
      ↓
Landing Screen
      ↓
Login Screen
      ↓
Dashboard
      ├── Attendance Tracking
      ├── Leave Management
      ├── Task Management
      ├── Client Visits
      └── Announcements
```

---

## 6. UI Design Guidelines

* Modern and clean UI
* Consistent layout across all screens
* Simple and intuitive navigation
* Strong focus on usability and clarity

---

## 7. Out of Scope

The following items are **explicitly excluded** from this phase:

* Admin panel
* Backend logic
* Authentication implementation details
* Push notifications
* Role or permission management
* Payroll or analytics features

---

## 8. Deliverables (Initial Phase)

* Application navigation structure
* All defined UI screens
* Screen-to-screen transitions

---

## 9. Notes

* ✅ This SRS is strictly aligned with the provided feature list
* ✅ No additional or hidden features are included
* ✅ Optimized for **AI-based Kotlin Multiplatform UI generation**

---

### 🚀 Next Possible Steps

You may proceed with any of the following:

* Convert this SRS into **KMP Navigation Graph**
* Generate **Compose Multiplatform screen stubs**
* Create **screen-wise AI UI prompts**
* Design **Bottom Navigation / Drawer structure**

Just tell me what you want to generate next 🚀

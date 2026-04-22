# 🏥 Elmas Hospital Management System

<div align="center">

![Python Version](https://img.shields.io/badge/Python-3.8+-blue?style=flat-square&logo=python)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=flat-square)

A comprehensive hospital management and patient administration system available in both Java and Python.

[Features](#-features) • [Installation](#-installation) • [Usage](#-usage) • [Project Structure](#-project-structure)

</div>

---

## 🌟 Features

### Patient Management
- 👤 Complete patient records with personal information
- 📞 Contact management and patient history
- 🔍 Advanced patient search and filtering
- 📋 Comprehensive medical documentation

### Medical Tracking
- 🩺 Symptom documentation (24+ symptoms supported)
- 🧪 Laboratory exam tracking and management
- 🏨 Medical imaging results (X-ray, CT, Ultrasound, etc.)
- 💊 Prescription and medication management
- 📊 Patient dashboard with medical overview

### Additional Features
- 💰 Salary calculation module for staff
- 🌍 Multi-country support (African countries + more)
- 🖼️ Photo management for patient identification
- 🔐 Secure patient data handling

---

## 📦 Installation

### Prerequisites
- **Python 3.8** or higher
- **Tkinter** (usually comes with Python)
- **Pillow** for image processing

### Setup Instructions

1. **Clone the repository**
```bash
git clone https://github.com/trevoradambulayima-prog/Elmas_Java_Projet.git
cd Elmas_Python_Projet
```

2. **Install dependencies**
```bash
pip install -r requirements.txt
```

3. **Run the application**
```bash
python main.py
```

---

## 🚀 Usage

### Starting the Application
```powershell
python main.py
```

### Main Features Walkthrough

1. **Login Screen** - Secure access to the hospital system
2. **Main Menu** - Navigate to different modules
3. **Patient Dashboard** - View and manage patient records
4. **Medical Records** - Add symptoms, exams, imaging, and prescriptions
5. **Salary Calculator** - Calculate staff salaries with various parameters
6. **Search** - Find patients by various criteria

### Supported Medical Data

**Symptoms (24 types)**
- Respiratory: Fever, Cough, Sore Throat, Shortness of Breath
- Digestive: Nausea, Vomiting, Diarrhea, Abdominal Pain
- Neurological: Dizziness, Confusion, Vision Issues
- General: Fatigue, Chills, Sweats, Muscle Pain

**Laboratory Exams**
- Blood Tests: RBC, WBC, Glucose, Cholesterol
- Samples: Stool, Urine
- Special: Creatinine

**Medical Imaging**
- Radiography, Scanner (CT), Ultrasound
- ECG, EEG, MRI, Dialysis

**Medications**
- Paracetamol, Amoxicilline, Ibuprofen
- Vitamin C, Omeprazole

---

## 📁 Project Structure

```
Elmas_Python_Projet/
├── main.py                    # Main application entry point
├── theme.py                   # Beautiful UI theme configuration
├── requirements.txt           # Python dependencies
├── README.md                  # This file
├── .gitignore                 # Git ignore rules
│
└── Elmas_Java_Projet/         # Original Java implementation
    ├── MenuPrincipal.java     # Main menu interface
    ├── GestionHopital.java    # Hospital management logic
    ├── MedicalDashboard.java  # Medical records dashboard
    └── CalculSalaireAgent.java # Salary calculation module
```

---

## 🎨 Technology Stack

| Component | Technology |
|-----------|-----------|
| **Frontend** | Tkinter (Python GUI) |
| **Backend** | Python 3.8+ |
| **Alternative** | Java Swing |
| **Data Storage** | In-memory (currently) |
| **Image Support** | Pillow |

---

## 💡 Key Components

### `main.py`
The main application featuring:
- Tkinter-based GUI with modern design
- Patient record management
- Medical data entry and tracking
- Scrollable, responsive interfaces
- Theme integration for beautiful UI

### `theme.py`
Beautiful design system with:
- Professional color palette (medical green + blues)
- Consistent typography
- Spacing and padding guidelines
- Pre-configured button and component styles

### Java Modules
- **MenuPrincipal.java** - Application launcher
- **GestionHopital.java** - Core hospital management logic
- **MedicalDashboard.java** - Medical records interface
- **CalculSalaireAgent.java** - Staff salary calculations

---

## 📋 System Requirements

| Requirement | Details |
|------------|---------|
| **OS** | Windows, macOS, Linux |
| **Python** | 3.8 or higher |
| **RAM** | 512 MB minimum |
| **Disk** | 50 MB for installation |
| **Display** | 1280x840 minimum resolution |

---

## 🔧 Configuration

Edit color scheme in `theme.py`:
```python
COLORS = {
    "primary": "#2E5266",      # Main color
    "accent": "#3BA272",       # Accent color
    "danger": "#D84C4C",       # Alert color
    # ... more colors
}
```

---

## 📝 Data Fields

### Patient Record
- **Fiche Number** - Unique patient identifier
- **First Name (Prénom)** - Patient first name
- **Last Name (Nom)** - Patient last name
- **Additional Name (Postnom)** - Third name
- **Phone Number** - Contact information
- **Details** - Additional notes and history

---

## 🤝 Contributing

Contributions are welcome! Please feel free to:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💻 Author

**Trevor Adam Bulayima**
- GitHub: [@trevoradambulayima-prog](https://github.com/trevoradambulayima-prog)
- Repository: [Elmas_Java_Projet](https://github.com/trevoradambulayima-prog/Elmas_Java_Projet)

---

## 🙏 Acknowledgments

- Tkinter community for the excellent GUI framework
- Medical field professionals for requirements and specifications
- All contributors and testers

---

## 📞 Support

For issues, questions, or suggestions, please:
- Open an issue on GitHub
- Check existing documentation
- Review the project structure

---

<div align="center">

**Made with ❤️ for better healthcare management**

⭐ If you find this project useful, please give it a star!

</div>


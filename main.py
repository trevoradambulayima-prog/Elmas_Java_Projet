from __future__ import annotations

import tkinter as tk
from dataclasses import dataclass
from pathlib import Path
from tkinter import filedialog, messagebox, ttk


APP_TITLE = "Gestion Hospitaliere"
WINDOW_SIZE = "1280x840"
PRIMARY = "#4d5968"
SURFACE = "#f0f2f5"
ACCENT = "#2e7d5a"
DANGER = "#9a4c4c"

SYMPTOMS = [
    "Fievre",
    "Frissons",
    "Fatigue intense",
    "Douleurs musculaires",
    "Maux de tete",
    "Sueurs",
    "Toux",
    "Mal de gorge",
    "Nez bouche",
    "Eternuements",
    "Essoufflement",
    "Oppression thoracique",
    "Nausees",
    "Vomissements",
    "Diarrhee",
    "Douleurs abdominales",
    "Perte d'appetit",
    "Reflux",
    "Etourdissements",
    "Vertiges",
    "Perte d'odorat",
    "Troubles de la vision",
    "Confusion",
    "Perte de connaissance",
]

LAB_EXAMS = [
    "GS",
    "VS",
    "Selles",
    "Urine",
    "Glycemie",
    "Cholesterol",
    "Globules rouges",
    "Globules blancs",
    "Creatinine",
]

IMAGING = ["Radiographie", "Scanner", "Echographie", "Dialyse", "ECG", "EEG", "IRM"]
MEDICINES = ["Paracetamol", "Amoxicilline", "Ibuprofene", "Vitamine C", "Omeprazole"]
COUNTRIES = [
    "RDC",
    "Congo Brazzaville",
    "Angola",
    "Rwanda",
    "Burundi",
    "Ouganda",
    "France",
    "Belgique",
    "Canada",
    "Autre",
]


@dataclass
class PatientRecord:
    fiche: str
    prenom: str
    nom: str
    postnom: str
    telephone: str
    details: str


class ScrollableFrame(ttk.Frame):
    def __init__(self, master: tk.Misc) -> None:
        super().__init__(master)
        canvas = tk.Canvas(self, bg=SURFACE, highlightthickness=0)
        scrollbar = ttk.Scrollbar(self, orient="vertical", command=canvas.yview)
        self.content = ttk.Frame(canvas)
        self.content.bind(
            "<Configure>",
            lambda event: canvas.configure(scrollregion=canvas.bbox("all")),
        )
        canvas.create_window((0, 0), window=self.content, anchor="nw")
        canvas.configure(yscrollcommand=scrollbar.set)
        canvas.pack(side="left", fill="both", expand=True)
        scrollbar.pack(side="right", fill="y")


class HospitalApp(tk.Tk):
    def __init__(self) -> None:
        super().__init__()
        self.title(APP_TITLE)
        self.geometry(WINDOW_SIZE)
        self.configure(bg=SURFACE)
        self.minsize(1100, 760)
        self.saved_patients: list[PatientRecord] = []
        self._configure_styles()

        self.container = ttk.Frame(self, padding=16)
        self.container.pack(fill="both", expand=True)

        self.frames: dict[str, ttk.Frame] = {}
        self._build_frames()
        self.show_frame("login")

    def _configure_styles(self) -> None:
        style = ttk.Style(self)
        style.theme_use("clam")
        style.configure("App.TFrame", background=SURFACE)
        style.configure("Card.TFrame", background="white")
        style.configure("Title.TLabel", background=SURFACE, foreground=PRIMARY, font=("Segoe UI", 22, "bold"))
        style.configure("Section.TLabelframe", background="white")
        style.configure("Section.TLabelframe.Label", background="white", foreground=PRIMARY, font=("Segoe UI", 11, "bold"))
        style.configure("Header.TLabel", background=PRIMARY, foreground="white", font=("Segoe UI", 12, "bold"), padding=12)
        style.configure("Primary.TButton", font=("Segoe UI", 10, "bold"))

    def _build_frames(self) -> None:
        self.frames["login"] = LoginFrame(self.container, self)
        self.frames["menu"] = MenuFrame(self.container, self)
        self.frames["patients"] = PatientDashboardFrame(self.container, self)
        self.frames["salary"] = SalaryCalculatorFrame(self.container, self)
        for frame in self.frames.values():
            frame.grid(row=0, column=0, sticky="nsew")
        self.container.columnconfigure(0, weight=1)
        self.container.rowconfigure(0, weight=1)

    def show_frame(self, name: str) -> None:
        self.frames[name].tkraise()


class LoginFrame(ttk.Frame):
    def __init__(self, master: tk.Misc, app: HospitalApp) -> None:
        super().__init__(master, style="App.TFrame")
        self.app = app

        shell = ttk.Frame(self, style="Card.TFrame", padding=32)
        shell.place(relx=0.5, rely=0.5, anchor="center", relwidth=0.45, relheight=0.55)

        ttk.Label(shell, text="Gestion Hospitaliere - Connexion", style="Title.TLabel").pack(anchor="center", pady=(0, 24))

        self.progress = ttk.Progressbar(shell, mode="determinate", maximum=100)
        self.progress.pack(fill="x", pady=(0, 8))
        self.progress_label = ttk.Label(shell, text="Initialisation...", background="white", foreground=PRIMARY, font=("Segoe UI", 10))
        self.progress_label.pack(anchor="w", pady=(0, 24))

        form = ttk.Frame(shell, style="Card.TFrame")
        form.pack(fill="x")
        ttk.Label(form, text="Nom d'utilisateur", background="white", foreground=PRIMARY).pack(anchor="w")
        self.username = ttk.Entry(form)
        self.username.pack(fill="x", pady=(4, 12))
        ttk.Label(form, text="Mot de passe", background="white", foreground=PRIMARY).pack(anchor="w")
        self.password = ttk.Entry(form, show="*")
        self.password.pack(fill="x", pady=(4, 20))

        buttons = ttk.Frame(shell, style="Card.TFrame")
        buttons.pack(fill="x")
        ttk.Button(buttons, text="Connexion", command=self.verify_login).pack(side="left", expand=True, fill="x", padx=(0, 8))
        ttk.Button(buttons, text="Quitter", command=self.app.destroy).pack(side="left", expand=True, fill="x")

        self.after(25, self._advance_progress, 0)

    def _advance_progress(self, value: int) -> None:
        if value > 100:
            self.progress_label.configure(text="Pret")
            return
        self.progress["value"] = value
        self.after(20, self._advance_progress, value + 2)

    def verify_login(self) -> None:
        allowed = {
            ("Etudiant", "123"),
            ("Parent", "456"),
            ("Bob OMARI", "java2026"),
        }
        if (self.username.get().strip(), self.password.get().strip()) in allowed:
            self.app.show_frame("menu")
        else:
            messagebox.showerror("Acces refuse", "Nom d'utilisateur ou mot de passe invalide.")


class MenuFrame(ttk.Frame):
    def __init__(self, master: tk.Misc, app: HospitalApp) -> None:
        super().__init__(master, style="App.TFrame")
        self.app = app

        ttk.Label(self, text="Menu Principal", style="Title.TLabel").pack(anchor="center", pady=(40, 24))
        ttk.Label(
            self,
            text="Version Python du projet Java de gestion hospitaliere",
            background=SURFACE,
            foreground=PRIMARY,
            font=("Segoe UI", 12),
        ).pack(anchor="center", pady=(0, 32))

        card = ttk.Frame(self, style="Card.TFrame", padding=28)
        card.pack(anchor="center")

        buttons = [
            ("Connexion", lambda: self.app.show_frame("login")),
            ("Gestion des malades", lambda: self.app.show_frame("patients")),
            ("Calcul de salaire", lambda: self.app.show_frame("salary")),
            ("Quitter", self.app.destroy),
        ]
        for label, action in buttons:
            ttk.Button(card, text=label, command=action).pack(fill="x", pady=8, ipadx=16, ipady=12)


class PatientDashboardFrame(ttk.Frame):
    def __init__(self, master: tk.Misc, app: HospitalApp) -> None:
        super().__init__(master, style="App.TFrame")
        self.app = app
        self.photo_path = tk.StringVar(value="Aucune photo selectionnee")

        header = ttk.Frame(self, style="Card.TFrame")
        header.pack(fill="x", pady=(0, 12))
        ttk.Label(header, text="Gestion des malades", style="Title.TLabel").pack(side="left", padx=12, pady=12)
        ttk.Button(header, text="Retour au menu", command=lambda: app.show_frame("menu")).pack(side="right", padx=12, pady=12)

        notebook = ttk.Notebook(self)
        notebook.pack(fill="both", expand=True)

        self.identity_vars = {name: tk.StringVar() for name in ["fiche", "prenom", "nom", "postnom", "telephone", "naissance", "tension", "temperature", "poids"]}
        self.sex_var = tk.StringVar(value="M")
        self.symptom_vars = {name: tk.BooleanVar(value=False) for name in SYMPTOMS}
        self.exam_vars = {name: tk.BooleanVar(value=False) for name in LAB_EXAMS}
        self.imaging_vars = {name: tk.BooleanVar(value=False) for name in IMAGING}
        self.interpretation_vars = {name: tk.StringVar() for name in IMAGING}
        self.diagnostic_vars = [tk.StringVar(value="Observation") for _ in range(3)]
        self.prescription_var = tk.StringVar()
        self.prescriptions: list[str] = []

        identity_tab = ScrollableFrame(notebook)
        exams_tab = ScrollableFrame(notebook)
        diagnostic_tab = ScrollableFrame(notebook)
        notebook.add(identity_tab, text="Identite")
        notebook.add(exams_tab, text="Examens")
        notebook.add(diagnostic_tab, text="Diagnostic")

        self._build_identity_tab(identity_tab.content)
        self._build_exams_tab(exams_tab.content)
        self._build_diagnostic_tab(diagnostic_tab.content)

    def _build_identity_tab(self, parent: ttk.Frame) -> None:
        info = ttk.LabelFrame(parent, text="Identite du malade", style="Section.TLabelframe", padding=16)
        info.pack(fill="x", padx=8, pady=8)
        grid_fields = [
            ("Numero fiche", "fiche"),
            ("Prenom", "prenom"),
            ("Nom", "nom"),
            ("Postnom", "postnom"),
            ("Telephone", "telephone"),
            ("Date de naissance", "naissance"),
            ("Tension arterielle", "tension"),
            ("Temperature", "temperature"),
            ("Poids", "poids"),
        ]
        for index, (label, key) in enumerate(grid_fields):
            ttk.Label(info, text=label, background="white", foreground=PRIMARY).grid(row=index // 2, column=(index % 2) * 2, sticky="w", padx=8, pady=6)
            ttk.Entry(info, textvariable=self.identity_vars[key], width=30).grid(row=index // 2, column=(index % 2) * 2 + 1, sticky="ew", padx=8, pady=6)
        info.columnconfigure(1, weight=1)
        info.columnconfigure(3, weight=1)

        sex_box = ttk.LabelFrame(parent, text="Sexe", style="Section.TLabelframe", padding=16)
        sex_box.pack(fill="x", padx=8, pady=8)
        ttk.Radiobutton(sex_box, text="Masculin", variable=self.sex_var, value="M").pack(side="left", padx=8)
        ttk.Radiobutton(sex_box, text="Feminin", variable=self.sex_var, value="F").pack(side="left", padx=8)

        photo = ttk.LabelFrame(parent, text="Photo", style="Section.TLabelframe", padding=16)
        photo.pack(fill="x", padx=8, pady=8)
        ttk.Label(photo, textvariable=self.photo_path, background="white", foreground=PRIMARY).pack(side="left", padx=(0, 12))
        ttk.Button(photo, text="Choisir une photo", command=self.select_photo).pack(side="left")

    def _build_exams_tab(self, parent: ttk.Frame) -> None:
        symptoms = ttk.LabelFrame(parent, text="Symptomes", style="Section.TLabelframe", padding=16)
        symptoms.pack(fill="x", padx=8, pady=8)
        for index, name in enumerate(SYMPTOMS):
            ttk.Checkbutton(symptoms, text=name, variable=self.symptom_vars[name]).grid(row=index // 4, column=index % 4, sticky="w", padx=8, pady=4)

        labs = ttk.LabelFrame(parent, text="Examens de laboratoire", style="Section.TLabelframe", padding=16)
        labs.pack(fill="x", padx=8, pady=8)
        for index, name in enumerate(LAB_EXAMS):
            ttk.Checkbutton(labs, text=name, variable=self.exam_vars[name]).grid(row=index // 3, column=index % 3, sticky="w", padx=8, pady=4)

        imaging = ttk.LabelFrame(parent, text="Imagerie et interpretation", style="Section.TLabelframe", padding=16)
        imaging.pack(fill="x", padx=8, pady=8)
        for row, name in enumerate(IMAGING):
            ttk.Checkbutton(imaging, text=name, variable=self.imaging_vars[name]).grid(row=row, column=0, sticky="w", padx=8, pady=4)
            ttk.Entry(imaging, textvariable=self.interpretation_vars[name], width=60).grid(row=row, column=1, sticky="ew", padx=8, pady=4)
        imaging.columnconfigure(1, weight=1)

    def _build_diagnostic_tab(self, parent: ttk.Frame) -> None:
        diagnostics = ttk.LabelFrame(parent, text="Diagnostic", style="Section.TLabelframe", padding=16)
        diagnostics.pack(fill="x", padx=8, pady=8)
        choices = ["Observation", "Paludisme", "Typhoide", "Pneumonie", "Infection", "Autre"]
        for index, var in enumerate(self.diagnostic_vars, start=1):
            ttk.Label(diagnostics, text=f"Hypothese {index}", background="white", foreground=PRIMARY).grid(row=index - 1, column=0, sticky="w", padx=8, pady=6)
            ttk.Combobox(diagnostics, textvariable=var, values=choices, state="readonly").grid(row=index - 1, column=1, sticky="ew", padx=8, pady=6)
        diagnostics.columnconfigure(1, weight=1)

        prescription = ttk.LabelFrame(parent, text="Prescription", style="Section.TLabelframe", padding=16)
        prescription.pack(fill="x", padx=8, pady=8)
        ttk.Combobox(prescription, textvariable=self.prescription_var, values=MEDICINES, state="readonly").grid(row=0, column=0, sticky="ew", padx=8, pady=6)
        ttk.Button(prescription, text="Ajouter", command=self.add_prescription).grid(row=0, column=1, padx=8, pady=6)
        prescription.columnconfigure(0, weight=1)

        self.prescription_list = tk.Listbox(prescription, height=6)
        self.prescription_list.grid(row=1, column=0, columnspan=2, sticky="ew", padx=8, pady=(6, 10))

        actions = ttk.Frame(parent, style="Card.TFrame")
        actions.pack(fill="x", padx=8, pady=8)
        ttk.Button(actions, text="Enregistrer", command=self.save_patient).pack(side="left", padx=(0, 8))
        ttk.Button(actions, text="Rechercher", command=self.search_patient).pack(side="left", padx=8)
        ttk.Button(actions, text="Reinitialiser", command=self.reset_form).pack(side="left", padx=8)

    def select_photo(self) -> None:
        selected = filedialog.askopenfilename(
            title="Selectionner une photo",
            filetypes=[("Images", "*.png *.jpg *.jpeg *.bmp *.gif"), ("Tous les fichiers", "*.*")],
        )
        if selected:
            self.photo_path.set(selected)

    def add_prescription(self) -> None:
        medicine = self.prescription_var.get().strip()
        if not medicine:
            return
        self.prescriptions.append(medicine)
        self.prescription_list.insert("end", medicine)
        self.prescription_var.set("")

    def _build_record(self) -> PatientRecord | None:
        fiche = self.identity_vars["fiche"].get().strip()
        prenom = self.identity_vars["prenom"].get().strip()
        nom = self.identity_vars["nom"].get().strip()
        if not any([fiche, prenom, nom]):
            return None
        details = "\n".join(
            [
                f"Postnom: {self.identity_vars['postnom'].get().strip()}",
                f"Telephone: {self.identity_vars['telephone'].get().strip()}",
                f"Naissance: {self.identity_vars['naissance'].get().strip()}",
                f"Sexe: {self.sex_var.get()}",
                f"Symptomes: {', '.join(name for name, var in self.symptom_vars.items() if var.get()) or 'Aucun'}",
                f"Examens: {', '.join(name for name, var in self.exam_vars.items() if var.get()) or 'Aucun'}",
                f"Imagerie: {', '.join(name for name, var in self.imaging_vars.items() if var.get()) or 'Aucune'}",
                f"Diagnostics: {', '.join(var.get() for var in self.diagnostic_vars)}",
                f"Prescription: {', '.join(self.prescriptions) or 'Aucune'}",
            ]
        )
        return PatientRecord(
            fiche=fiche,
            prenom=prenom,
            nom=nom,
            postnom=self.identity_vars["postnom"].get().strip(),
            telephone=self.identity_vars["telephone"].get().strip(),
            details=details,
        )

    def save_patient(self) -> None:
        record = self._build_record()
        if record is None:
            messagebox.showwarning("Enregistrement impossible", "Remplissez au moins le numero de fiche, le prenom ou le nom.")
            return
        self.app.saved_patients = [
            existing for existing in self.app.saved_patients if existing.fiche != record.fiche or not record.fiche
        ]
        self.app.saved_patients.append(record)
        messagebox.showinfo("Enregistrement", "Patient enregistre en memoire avec succes.")

    def search_patient(self) -> None:
        if not self.app.saved_patients:
            record = self._build_record()
            if record:
                self.app.saved_patients.append(record)
        if not self.app.saved_patients:
            messagebox.showinfo("Recherche", "Aucun patient enregistre pour le moment.")
            return

        dialog = tk.Toplevel(self)
        dialog.title("Recherche patient")
        dialog.geometry("680x420")
        dialog.configure(bg=SURFACE)

        query = tk.StringVar()
        ttk.Label(dialog, text="Terme de recherche", background=SURFACE, foreground=PRIMARY).pack(anchor="w", padx=16, pady=(16, 6))
        ttk.Entry(dialog, textvariable=query).pack(fill="x", padx=16)
        results = tk.Text(dialog, wrap="word", font=("Consolas", 10))
        results.pack(fill="both", expand=True, padx=16, pady=16)

        def run_search() -> None:
            term = query.get().strip().lower()
            matches = []
            for patient in self.app.saved_patients:
                haystack = f"{patient.fiche} {patient.prenom} {patient.nom} {patient.postnom} {patient.telephone} {patient.details}".lower()
                if term and term in haystack:
                    matches.append(patient)
            results.delete("1.0", "end")
            if not term:
                results.insert("end", "Entrez un terme de recherche.")
                return
            if not matches:
                results.insert("end", "Aucun resultat trouve.")
                return
            for patient in matches:
                results.insert("end", f"{patient.prenom} {patient.nom} | Fiche: {patient.fiche}\n")
                results.insert("end", patient.details + "\n")
                results.insert("end", "-" * 72 + "\n")

        ttk.Button(dialog, text="Rechercher", command=run_search).pack(anchor="e", padx=16, pady=(0, 16))

    def reset_form(self) -> None:
        for var in self.identity_vars.values():
            var.set("")
        for var in self.symptom_vars.values():
            var.set(False)
        for var in self.exam_vars.values():
            var.set(False)
        for var in self.imaging_vars.values():
            var.set(False)
        for var in self.interpretation_vars.values():
            var.set("")
        for var in self.diagnostic_vars:
            var.set("Observation")
        self.sex_var.set("M")
        self.photo_path.set("Aucune photo selectionnee")
        self.prescription_var.set("")
        self.prescriptions.clear()
        self.prescription_list.delete(0, "end")


class SalaryCalculatorFrame(ttk.Frame):
    def __init__(self, master: tk.Misc, app: HospitalApp) -> None:
        super().__init__(master, style="App.TFrame")
        self.app = app
        self.photo_path = tk.StringVar(value="Aucune photo selectionnee")

        header = ttk.Frame(self, style="Card.TFrame")
        header.pack(fill="x", pady=(0, 12))
        ttk.Label(header, text="Calcul de salaire des agents", style="Title.TLabel").pack(side="left", padx=12, pady=12)
        ttk.Button(header, text="Retour au menu", command=lambda: app.show_frame("menu")).pack(side="right", padx=12, pady=12)

        self.identity_vars = {name: tk.StringVar() for name in [
            "matricule",
            "prenom",
            "nom",
            "postnom",
            "naissance",
            "lieu",
            "telephone",
            "email",
            "universitaires",
            "secondaires",
            "formations",
            "nom_etudiant",
            "promotion",
            "filiere",
            "village",
            "territoire",
            "district",
            "province",
        ]}
        self.sex_var = tk.StringVar(value="Selectionner")
        self.country_var = tk.StringVar(value=COUNTRIES[0])
        self.year_var = tk.StringVar(value="2026")
        self.month_var = tk.StringVar(value="Janvier")
        self.direction_var = tk.StringVar(value="Direction Generale")
        self.division_var = tk.StringVar(value="Division 1")
        self.bureau_var = tk.StringVar(value="Bureau A")
        self.cellule_var = tk.StringVar(value="Cellule 1")

        self.input_vars = {name: tk.StringVar(value="0") for name in [
            "base",
            "prime_rendement",
            "prime_anciennete",
            "prime_risque",
            "heures_sup",
            "participation",
            "gratification",
            "transport",
            "logement",
            "repas",
            "conge",
        ]}
        self.output_vars = {name: tk.StringVar(value="0.00") for name in ["ipr", "cnss", "inpp", "total_primes", "total_retenues", "brut", "net"]}

        notebook = ttk.Notebook(self)
        notebook.pack(fill="both", expand=True)
        profile_tab = ScrollableFrame(notebook)
        pay_tab = ScrollableFrame(notebook)
        notebook.add(profile_tab, text="Profil")
        notebook.add(pay_tab, text="Paie")

        self._build_profile_tab(profile_tab.content)
        self._build_pay_tab(pay_tab.content)

    def _build_profile_tab(self, parent: ttk.Frame) -> None:
        identity = ttk.LabelFrame(parent, text="Identite de l'agent", style="Section.TLabelframe", padding=16)
        identity.pack(fill="x", padx=8, pady=8)
        fields = [
            ("Matricule", "matricule"),
            ("Prenom", "prenom"),
            ("Nom", "nom"),
            ("Postnom", "postnom"),
            ("Date de naissance", "naissance"),
            ("Lieu de naissance", "lieu"),
            ("Telephone", "telephone"),
            ("Email", "email"),
        ]
        for index, (label, key) in enumerate(fields):
            ttk.Label(identity, text=label, background="white", foreground=PRIMARY).grid(row=index // 2, column=(index % 2) * 2, sticky="w", padx=8, pady=6)
            ttk.Entry(identity, textvariable=self.identity_vars[key]).grid(row=index // 2, column=(index % 2) * 2 + 1, sticky="ew", padx=8, pady=6)
        ttk.Label(identity, text="Sexe", background="white", foreground=PRIMARY).grid(row=4, column=0, sticky="w", padx=8, pady=6)
        ttk.Combobox(identity, textvariable=self.sex_var, values=["Selectionner", "Masculin", "Feminin"], state="readonly").grid(row=4, column=1, sticky="ew", padx=8, pady=6)
        identity.columnconfigure(1, weight=1)
        identity.columnconfigure(3, weight=1)

        origin = ttk.LabelFrame(parent, text="Origine", style="Section.TLabelframe", padding=16)
        origin.pack(fill="x", padx=8, pady=8)
        origin_fields = [("Village", "village"), ("Territoire", "territoire"), ("District", "district"), ("Province", "province")]
        for index, (label, key) in enumerate(origin_fields):
            ttk.Label(origin, text=label, background="white", foreground=PRIMARY).grid(row=index, column=0, sticky="w", padx=8, pady=6)
            ttk.Entry(origin, textvariable=self.identity_vars[key]).grid(row=index, column=1, sticky="ew", padx=8, pady=6)
        ttk.Label(origin, text="Pays", background="white", foreground=PRIMARY).grid(row=4, column=0, sticky="w", padx=8, pady=6)
        ttk.Combobox(origin, textvariable=self.country_var, values=COUNTRIES, state="readonly").grid(row=4, column=1, sticky="ew", padx=8, pady=6)
        origin.columnconfigure(1, weight=1)

        studies = ttk.LabelFrame(parent, text="Etudes et photo", style="Section.TLabelframe", padding=16)
        studies.pack(fill="x", padx=8, pady=8)
        study_fields = [("Etudes universitaires", "universitaires"), ("Etudes secondaires", "secondaires"), ("Autres formations", "formations"), ("Nom etudiant", "nom_etudiant"), ("Promotion", "promotion"), ("Filiere", "filiere")]
        for index, (label, key) in enumerate(study_fields):
            ttk.Label(studies, text=label, background="white", foreground=PRIMARY).grid(row=index, column=0, sticky="w", padx=8, pady=6)
            ttk.Entry(studies, textvariable=self.identity_vars[key]).grid(row=index, column=1, sticky="ew", padx=8, pady=6)
        ttk.Label(studies, textvariable=self.photo_path, background="white", foreground=PRIMARY).grid(row=0, column=2, sticky="w", padx=20, pady=6)
        ttk.Button(studies, text="Choisir une photo", command=self.select_photo).grid(row=1, column=2, sticky="w", padx=20, pady=6)
        studies.columnconfigure(1, weight=1)

    def _build_pay_tab(self, parent: ttk.Frame) -> None:
        salary = ttk.LabelFrame(parent, text="Salaire fixe et primes", style="Section.TLabelframe", padding=16)
        salary.pack(fill="x", padx=8, pady=8)
        salary_fields = [
            ("Salaire de base", "base"),
            ("Prime de rendement", "prime_rendement"),
            ("Prime d'anciennete", "prime_anciennete"),
            ("Prime de risque", "prime_risque"),
            ("Heures supplementaires", "heures_sup"),
            ("Participation aux benefices", "participation"),
            ("Gratification 13e mois", "gratification"),
        ]
        for index, (label, key) in enumerate(salary_fields):
            ttk.Label(salary, text=label, background="white", foreground=PRIMARY).grid(row=index, column=0, sticky="w", padx=8, pady=6)
            ttk.Entry(salary, textvariable=self.input_vars[key]).grid(row=index, column=1, sticky="ew", padx=8, pady=6)
        salary.columnconfigure(1, weight=1)

        allowance = ttk.LabelFrame(parent, text="Indemnites et service", style="Section.TLabelframe", padding=16)
        allowance.pack(fill="x", padx=8, pady=8)
        allowance_fields = [("Transport", "transport"), ("Logement", "logement"), ("Repas", "repas"), ("Conge", "conge")]
        for index, (label, key) in enumerate(allowance_fields):
            ttk.Label(allowance, text=label, background="white", foreground=PRIMARY).grid(row=index, column=0, sticky="w", padx=8, pady=6)
            ttk.Entry(allowance, textvariable=self.input_vars[key]).grid(row=index, column=1, sticky="ew", padx=8, pady=6)

        selectors = [
            ("Annee", self.year_var, [str(year) for year in range(2024, 2032)]),
            ("Mois", self.month_var, ["Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"]),
            ("Direction", self.direction_var, ["Direction Generale", "Direction RH", "Direction Finance", "Direction IT", "Direction Operations"]),
            ("Division", self.division_var, ["Division 1", "Division 2", "Division 3"]),
            ("Bureau", self.bureau_var, ["Bureau A", "Bureau B", "Bureau C"]),
            ("Cellule", self.cellule_var, ["Cellule 1", "Cellule 2", "Cellule 3"]),
        ]
        for offset, (label, var, choices) in enumerate(selectors, start=4):
            ttk.Label(allowance, text=label, background="white", foreground=PRIMARY).grid(row=offset, column=0, sticky="w", padx=8, pady=6)
            ttk.Combobox(allowance, textvariable=var, values=choices, state="readonly").grid(row=offset, column=1, sticky="ew", padx=8, pady=6)
        allowance.columnconfigure(1, weight=1)

        results = ttk.LabelFrame(parent, text="Retenues et resultats", style="Section.TLabelframe", padding=16)
        results.pack(fill="x", padx=8, pady=8)
        result_labels = [
            ("IPR (3%)", "ipr"),
            ("CNSS (4%)", "cnss"),
            ("INPP", "inpp"),
            ("Total primes", "total_primes"),
            ("Total retenues", "total_retenues"),
            ("Salaire brut", "brut"),
            ("Salaire net", "net"),
        ]
        for index, (label, key) in enumerate(result_labels):
            ttk.Label(results, text=label, background="white", foreground=PRIMARY).grid(row=index, column=0, sticky="w", padx=8, pady=6)
            ttk.Entry(results, textvariable=self.output_vars[key], state="readonly").grid(row=index, column=1, sticky="ew", padx=8, pady=6)
        results.columnconfigure(1, weight=1)

        actions = ttk.Frame(parent, style="Card.TFrame")
        actions.pack(fill="x", padx=8, pady=8)
        ttk.Button(actions, text="Calculer", command=self.calculate_salary).pack(side="left", padx=(0, 8))
        ttk.Button(actions, text="Enregistrer", command=self.save_salary_sheet).pack(side="left", padx=8)
        ttk.Button(actions, text="Reinitialiser", command=self.reset_form).pack(side="left", padx=8)

    def select_photo(self) -> None:
        selected = filedialog.askopenfilename(
            title="Selectionner une photo",
            filetypes=[("Images", "*.png *.jpg *.jpeg *.bmp *.gif"), ("Tous les fichiers", "*.*")],
        )
        if selected:
            self.photo_path.set(selected)

    def calculate_salary(self) -> None:
        try:
            base = float(self.input_vars["base"].get())
            prime_rendement = float(self.input_vars["prime_rendement"].get())
            prime_anciennete = float(self.input_vars["prime_anciennete"].get())
            prime_risque = float(self.input_vars["prime_risque"].get())
            heures_sup = float(self.input_vars["heures_sup"].get())
            participation = float(self.input_vars["participation"].get())
            gratification = float(self.input_vars["gratification"].get())
            transport = float(self.input_vars["transport"].get())
            logement = float(self.input_vars["logement"].get())
            repas = float(self.input_vars["repas"].get())
            conge = float(self.input_vars["conge"].get())
        except ValueError:
            messagebox.showerror("Erreur de saisie", "Tous les champs de paie doivent contenir des nombres valides.")
            return

        total_primes = prime_rendement + prime_anciennete + prime_risque + heures_sup + participation + gratification
        total_indemnites = transport + logement + repas + conge
        brut = base + total_primes + total_indemnites
        ipr = brut * 0.03
        cnss = brut * 0.04
        inpp = 0.0
        total_retenues = ipr + cnss + inpp
        net = brut - total_retenues

        values = {
            "ipr": ipr,
            "cnss": cnss,
            "inpp": inpp,
            "total_primes": total_primes,
            "total_retenues": total_retenues,
            "brut": brut,
            "net": net,
        }
        for key, value in values.items():
            self.output_vars[key].set(f"{value:.2f}")
        messagebox.showinfo("Calcul termine", "Le calcul de salaire a ete effectue avec succes.")

    def save_salary_sheet(self) -> None:
        if not self.identity_vars["matricule"].get().strip():
            messagebox.showwarning("Champ obligatoire", "Le matricule doit etre renseigne avant l'enregistrement.")
            return
        if self.output_vars["net"].get() == "0.00":
            self.calculate_salary()
        messagebox.showinfo("Enregistrement", "La fiche de paie a ete preparee dans l'application.")

    def reset_form(self) -> None:
        for var in self.identity_vars.values():
            var.set("")
        self.sex_var.set("Selectionner")
        self.country_var.set(COUNTRIES[0])
        self.year_var.set("2026")
        self.month_var.set("Janvier")
        self.direction_var.set("Direction Generale")
        self.division_var.set("Division 1")
        self.bureau_var.set("Bureau A")
        self.cellule_var.set("Cellule 1")
        for var in self.input_vars.values():
            var.set("0")
        for var in self.output_vars.values():
            var.set("0.00")
        self.photo_path.set("Aucune photo selectionnee")


def main() -> None:
    app = HospitalApp()
    app.iconname(APP_TITLE)
    app.mainloop()


if __name__ == "__main__":
    main()

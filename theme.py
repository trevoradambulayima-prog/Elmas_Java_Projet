"""
Beautiful theme configuration for Elmas Hospital Management System
"""

# Color Palette - Modern Medical Theme
COLORS = {
    "primary": "#2E5266",          # Deep Blue
    "secondary": "#6E8898",        # Steel Blue
    "accent": "#3BA272",           # Medical Green
    "danger": "#D84C4C",           # Medical Red
    "warning": "#F5A623",          # Warning Orange
    "success": "#27AE60",          # Success Green
    "surface": "#F8F9FA",          # Light Background
    "surface_alt": "#FFFFFF",      # White Background
    "text_primary": "#1A1A1A",     # Dark Text
    "text_secondary": "#6C757D",   # Gray Text
    "border": "#E0E0E0",           # Border Gray
    "hover": "#2A4A63",            # Hover State
}

# Fonts
FONTS = {
    "title": ("Segoe UI", 20, "bold"),
    "heading": ("Segoe UI", 14, "bold"),
    "subheading": ("Segoe UI", 12, "bold"),
    "body": ("Segoe UI", 11),
    "small": ("Segoe UI", 9),
}

# Spacing
PADDING = {
    "xs": 4,
    "sm": 8,
    "md": 12,
    "lg": 16,
    "xl": 20,
    "xxl": 24,
}

# Border Radius (for modern look)
BORDER_RADIUS = 6
SHADOW_OFFSET = 2

# Window Configuration
WINDOW_SIZE = "1280x840"
BACKGROUND_COLOR = COLORS["surface"]
TEXT_COLOR = COLORS["text_primary"]

# Component Styles
BUTTON_STYLE = {
    "bg": COLORS["accent"],
    "fg": "white",
    "activebackground": COLORS["success"],
    "activeforeground": "white",
    "font": FONTS["body"],
    "relief": "flat",
    "cursor": "hand2",
    "bd": 0,
    "padx": PADDING["lg"],
    "pady": PADDING["md"],
}

BUTTON_DANGER = {
    **BUTTON_STYLE,
    "bg": COLORS["danger"],
    "activebackground": "#C93C3C",
}

BUTTON_SECONDARY = {
    **BUTTON_STYLE,
    "bg": COLORS["secondary"],
    "activebackground": COLORS["hover"],
}

ENTRY_STYLE = {
    "font": FONTS["body"],
    "bg": COLORS["surface_alt"],
    "fg": COLORS["text_primary"],
    "relief": "solid",
    "bd": 1,
    "insertbackground": COLORS["accent"],
}

LABEL_STYLE = {
    "font": FONTS["body"],
    "bg": COLORS["surface"],
    "fg": COLORS["text_primary"],
}

LABEL_HEADING = {
    "font": FONTS["heading"],
    "bg": COLORS["surface"],
    "fg": COLORS["primary"],
}

# Tips for Beautiful UI
TIPS = {
    "use_ttk": "Use ttk widgets for modern appearance",
    "consistent_colors": "Stick to the color palette",
    "proper_spacing": "Use consistent padding and margins",
    "clear_hierarchy": "Make important elements stand out",
    "accessible_fonts": "Use sans-serif fonts for clarity",
}

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
    "button": ("Segoe UI", 10, "bold"),
    "button_large": ("Segoe UI", 11, "bold"),
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

# Professional Button Styles - Enterprise Grade
BUTTON_STYLES = {
    # Primary Action Button (e.g., Save, Submit)
    "primary": {
        "bg": COLORS["primary"],
        "fg": "white",
        "activebackground": COLORS["hover"],
        "activeforeground": "white",
        "font": FONTS["button"],
        "relief": "flat",
        "cursor": "hand2",
        "bd": 0,
        "padx": PADDING["lg"],
        "pady": PADDING["md"],
        "highlightthickness": 0,
        "overrelief": "raised",
    },
    
    # Add Button (Accent Color - Green)
    "add": {
        "bg": COLORS["accent"],
        "fg": "white",
        "activebackground": COLORS["success"],
        "activeforeground": "white",
        "font": FONTS["button_large"],
        "relief": "flat",
        "cursor": "hand2",
        "bd": 0,
        "padx": PADDING["xl"],
        "pady": PADDING["lg"],
        "highlightthickness": 0,
        "overrelief": "raised",
    },
    
    # Secondary Button
    "secondary": {
        "bg": COLORS["secondary"],
        "fg": "white",
        "activebackground": COLORS["hover"],
        "activeforeground": "white",
        "font": FONTS["button"],
        "relief": "flat",
        "cursor": "hand2",
        "bd": 0,
        "padx": PADDING["lg"],
        "pady": PADDING["md"],
        "highlightthickness": 0,
        "overrelief": "raised",
    },
    
    # Danger Button (Delete, Remove)
    "danger": {
        "bg": COLORS["danger"],
        "fg": "white",
        "activebackground": "#C93C3C",
        "activeforeground": "white",
        "font": FONTS["button"],
        "relief": "flat",
        "cursor": "hand2",
        "bd": 0,
        "padx": PADDING["lg"],
        "pady": PADDING["md"],
        "highlightthickness": 0,
        "overrelief": "raised",
    },
    
    # Warning Button
    "warning": {
        "bg": COLORS["warning"],
        "fg": "white",
        "activebackground": "#E89000",
        "activeforeground": "white",
        "font": FONTS["button"],
        "relief": "flat",
        "cursor": "hand2",
        "bd": 0,
        "padx": PADDING["lg"],
        "pady": PADDING["md"],
        "highlightthickness": 0,
        "overrelief": "raised",
    },
    
    # Success Button
    "success": {
        "bg": COLORS["success"],
        "fg": "white",
        "activebackground": "#1E7E34",
        "activeforeground": "white",
        "font": FONTS["button"],
        "relief": "flat",
        "cursor": "hand2",
        "bd": 0,
        "padx": PADDING["lg"],
        "pady": PADDING["md"],
        "highlightthickness": 0,
        "overrelief": "raised",
    },
    
    # Subtle/Text Button
    "subtle": {
        "bg": COLORS["surface"],
        "fg": COLORS["primary"],
        "activebackground": COLORS["border"],
        "activeforeground": COLORS["primary"],
        "font": FONTS["button"],
        "relief": "flat",
        "cursor": "hand2",
        "bd": 1,
        "padx": PADDING["lg"],
        "pady": PADDING["md"],
        "highlightthickness": 0,
        "overrelief": "raised",
        "borderwidth": 1,
    },
}

# Component Styles
BUTTON_STYLE = BUTTON_STYLES["primary"]

BUTTON_DANGER = BUTTON_STYLES["danger"]

BUTTON_SECONDARY = BUTTON_STYLES["secondary"]

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
    "professional_buttons": "Use gradient-like effects with color transitions",
    "hover_feedback": "Provide visual feedback on interaction",
}

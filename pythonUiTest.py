import sys
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QTextEdit, QVBoxLayout
from PyQt5.QtGui import QPixmap, QFont
from PyQt5.QtCore import Qt

class UiWindow(QWidget):
    def __init__(self):
        super().__init__()

        # Set window properties
        self.setWindowTitle("ImageReader")
        self.setGeometry(100, 100, 800, 600)

        # Load the background image
        self.background_image = QPixmap("image.jpg")  # Make sure to have this image in the same directory

        # Create a label to display the background image
        self.background_label = QLabel(self)
        self.background_label.setPixmap(self.background_image)
        self.background_label.setScaledContents(True)  # Scale the image to fit the window

        # Create a text edit widget with fixed text
        self.text_edit = QTextEdit(self)
        self.text_edit.setStyleSheet("background: rgba(255, 255, 255, 0.0); font-size: 18px;")  # Semi-transparent background
        self.text_edit.setFrameShape(QTextEdit.NoFrame)  # Remove the border
        self.text_edit.setText("This is some static text for reading and copying.")  # Set static text
        self.text_edit.setReadOnly(True)  # Make the text box non-editable but highlightable
        self.text_edit.setAlignment(Qt.AlignCenter)  # Center-align text within the box

        # Create a layout and add widgets with alignment
        layout = QVBoxLayout(self)
        layout.addStretch(1)  # Add space at the top
        layout.addWidget(self.text_edit, alignment=Qt.AlignCenter)
        layout.addStretch(1)  # Add space at the bottom
        self.setLayout(layout)

        # Ensure the text edit is on top of the background label
        self.text_edit.raise_()  # Bring the text edit to the front

        # Initial font size adjustment
        self.adjust_font_size()

    def resizeEvent(self, event):
        # Resize the background label to match the window size
        self.background_label.resize(self.size())
        # Adjust the font size based on the new window size
        self.adjust_font_size()

    def adjust_font_size(self):
        # Calculate a font size based on the window height
        font_size = max(10, int(self.height() * 0.03))  # Scale font size based on window height
        font = QFont("Arial", font_size)
        self.text_edit.setFont(font)

        # Force the text edit widget to update its content layout after changing font
        self.text_edit.document().adjustSize()
    
if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = UiWindow()
    window.show()
    sys.exit(app.exec_())

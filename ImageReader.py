import cv2
import pytesseract
import matplotlib.pyplot as plt
from collections import defaultdict
from matplotlib.widgets import TextBox

filename = 'image.png'

# Read the image
img = cv2.imread(filename)

# Run Tesseract, returning detailed data
data = pytesseract.image_to_data(img, output_type=pytesseract.Output.DICT)

# Get image dimensions
h, w, _ = img.shape  # Assumes color image

# Create a Matplotlib figure
plt.figure(figsize=(10, 8))
plt.imshow(cv2.cvtColor(img, cv2.COLOR_BGR2RGB))
plt.axis('off')  # Hide axes

# Create a dictionary to group words by line
lines = defaultdict(list)

# Loop through the data and group words by their line number
for i in range(len(data['level'])):
    if data['text'][i].strip():  # Only consider non-empty text
        line_num = data['line_num'][i]
        lines[line_num].append(i)

# Draw bounding boxes and text for each line
# Render the lines in the correct order
for line_num in sorted(lines.keys()):  # Sort the line numbers to iterate from top to bottom
    word_indices = lines[line_num]  # Get the indices for this line
    for i in word_indices:
        x = data['left'][i]
        y = data['top'][i]
        width = data['width'][i]
        height = data['height'][i]

        # Correct the y-coordinate for Matplotlib
        y_matplotlib = (y + height)  # Invert Y-coordinate for correct rendering
    
        # Add the text
        plt.text(x, y_matplotlib, data['text'][i], color='red', fontsize=20,
                 ha='left', va='bottom', bbox=dict(facecolor='yellow', alpha=0.5))


plt.show()  # Display the image with annotations

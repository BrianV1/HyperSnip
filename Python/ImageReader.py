import sys
import cv2
import pytesseract
from collections import defaultdict
import os

filename = 'image.png'


# Read the image
img = cv2.imread(filename)

# Run Tesseract, returning detailed data
pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'
data = pytesseract.image_to_data(img, output_type=pytesseract.Output.DICT)
print(data)



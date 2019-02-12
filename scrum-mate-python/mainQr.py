import cv2
import pytesseract
from pyzbar.pyzbar import decode, ZBarSymbol


def crop_image(image, start_column, end_column):
    height, width = image.shape[:2]
    start_row, end_row = int(0), int(height)
    cropped_image = image[start_row:end_row, start_column:end_column]
    return cropped_image


def split_image_to_columns(image):
    columns = []
    height, width = image.shape[:2]

    decoded = decode(image, symbols=[ZBarSymbol.QRCODE])

    qr_dic = {}
    for qr in decoded:
        x = qr[2][0]  # The Left position of the QR code
        qr_dic[x] = qr[0]  # The Data stored in the QR code

    sorted_qr_list = sorted(qr_dic.keys())

    for i in range(len(sorted_qr_list)):
        x_axis = sorted_qr_list[i]
        start_column = x_axis

        if i < len(sorted_qr_list) - 1:
            end_column = sorted_qr_list[i + 1]
        else:
            end_column = width

        cropped_image = crop_image(image, start_column, end_column)
        columns.append(cropped_image)

    return columns


# (.*)-\\d+ TODO change with regex
def get_ticket_numbers(text):
    words = text.split()
    tickets = []
    for word in words:
        if '-' in word:
            tickets.append(word)
    return tickets


img = cv2.imread('AgileBoardQr.jpg')
columns = split_image_to_columns(img)

for i in range(len(columns)):
    cv2.imwrite('column' + str(i) + '.jpg', columns[i])

for i in range(len(columns)):
    text = pytesseract.image_to_string(columns[i])
    tickets = get_ticket_numbers(text)
    print('column' + str(i))
    print(tickets)

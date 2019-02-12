import cv2
import pytesseract
import re
from pyzbar.pyzbar import decode, ZBarSymbol

JIRA_TICKET_PREFIX = 'SCRM8'


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

    return columns, list(qr_dic.values())


def get_ticket_numbers(text, prefix):
    return re.findall(f"{prefix}-[0-9]\\d*", text)


def get_board(path):
    img = cv2.imread(path)
    columns, labels = split_image_to_columns(img)

    for i in range(len(columns)):
        cv2.imwrite('column' + str(i) + '.jpg', columns[i])

    tickets_dic = {}
    for i in range(len(columns)):
        text = pytesseract.image_to_string(columns[i])
        tickets_dic[labels[i]] = get_ticket_numbers(text, JIRA_TICKET_PREFIX)

    return tickets_dic


if __name__ == "__main__":
    board = get_board('AgileBoardQr6.jpg')
    print(board)

import json
import re

import cv2
import numpy as np
import pytesseract
from pyzbar.pyzbar import decode, ZBarSymbol

from service.jira_service import update_tickets

JIRA_TICKET_PREFIX = 'SCRM8'


def crop_image(image, start_column, end_column):
    height, width = image.shape[:2]
    start_row, end_row = int(0), int(height)
    cropped_image = image[start_row:end_row, start_column:end_column]
    return cropped_image


def split_image_to_columns(image):
    columns_dic = {}
    height, width = image.shape[:2]

    decoded = decode(image, symbols=[ZBarSymbol.QRCODE])

    qr_dic = {}
    for qr in decoded:
        x = qr[2][0]  # The Left position of the QR code
        qr_dic[x] = str(qr[0], 'utf-8')  # The Data stored in the QR code

    sorted_qr_list = sorted(qr_dic.keys())

    for i in range(len(sorted_qr_list)):
        x_axis = sorted_qr_list[i]
        start_column = x_axis

        if i < len(sorted_qr_list) - 1:
            end_column = sorted_qr_list[i + 1]
        else:
            end_column = width

        cropped_image = crop_image(image, start_column, end_column)
        columns_dic[qr_dic[x_axis]] = cropped_image

    return columns_dic


def get_ticket_numbers(text, prefix):
    return re.findall(f"{prefix}-[0-9]\\d*", text)


def process(image_file):
    img = cv2.imdecode(np.frombuffer(image_file, np.uint8), -1)
    columns_dic = split_image_to_columns(img)

    tickets_dic = {}
    for status in columns_dic.keys():
        text = pytesseract.image_to_string(columns_dic[status])
        tickets = get_ticket_numbers(text, JIRA_TICKET_PREFIX)
        # print(tickets)
        text2 = pytesseract.image_to_string(columns_dic[status], config="--psm 6")
        tickets2 = get_ticket_numbers(text2, JIRA_TICKET_PREFIX)
        # print(tickets2)
        text3 = pytesseract.image_to_string(columns_dic[status], config="--psm 11")
        tickets3 = get_ticket_numbers(text3, JIRA_TICKET_PREFIX)
        # print(tickets3)
        tickets_dic[status] = list(set(tickets + tickets2 + tickets3))

    return json.dumps(update_tickets(tickets_dic))

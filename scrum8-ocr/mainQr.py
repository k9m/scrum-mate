import cv2
import pytesseract

from service.ocr_service import get_ticket_numbers, split_image_to_columns

JIRA_TICKET_PREFIX = 'SCRM8'


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
    board = get_board('AgileBoardQr.jpg')
    print(board)

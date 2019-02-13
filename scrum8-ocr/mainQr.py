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
        tickets = get_ticket_numbers(text, JIRA_TICKET_PREFIX)
        print(tickets)
        text2 = pytesseract.image_to_string(columns[i], config="--psm 6")
        tickets2 = get_ticket_numbers(text2, JIRA_TICKET_PREFIX)
        print(tickets2)
        text3 = pytesseract.image_to_string(columns[i], config="--psm 11")
        tickets3 = get_ticket_numbers(text3, JIRA_TICKET_PREFIX)
        print(tickets3)
        tickets_dic[labels[i]] = list(set(tickets + tickets2 + tickets3))

    return tickets_dic


if __name__ == "__main__":
    board = get_board('AgileBoardQr.jpg')
    print(board)

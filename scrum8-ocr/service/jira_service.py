import requests

JIRA_UPDATE_URL = 'http://localhost:8082/tickets/'


def update_tickets(tickets_dic):
    response_dic = {}
    for status, tickets in tickets_dic.items():
        for ticket in tickets:
            payload = {'targetState': status}
            response = requests.post(JIRA_UPDATE_URL + ticket, json=payload)
            if response.ok:
                response_dic[ticket] = 'Updated to ' + status
            else:
                response_dic[ticket] = response.json()['message']
    return response_dic

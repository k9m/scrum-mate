from unittest import TestCase

from service.ocr_service import get_ticket_numbers


class TestGetTicketNumbers(TestCase):
    def test_get_ticket_numbers(self):
        text = """
            To Do Progess Done
            SCRM8-4
            Another thing that needs to be done
            
            SCRM8-10 =f |SCRM8-5
            
            |
            
            a Harry Potter and the
            Philosopher's Stone
            :
            |
            :
            a ; a: eSCRM8-2 .
            _ | This is a test ticket |SCRM8-1
            
            The first thing we need
            | to do
            SCRM8-3
        """
        x = get_ticket_numbers(text, 'SCRM8')
        self.assertEqual(len(x), 6)
        self.assertIn('SCRM8-4', x)
        self.assertIn('SCRM8-10', x)
        self.assertIn('SCRM8-5', x)
        self.assertIn('SCRM8-2', x)
        self.assertIn('SCRM8-1', x)
        self.assertIn('SCRM8-3', x)

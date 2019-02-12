import cherrypy

from service.ocr_service import process


class Root(object):

    @cherrypy.expose
    def upload(self, file):
        size = 0
        all_data = bytearray()
        while True:
            data = file.file.read(8192)
            all_data += data
            if not data:
                break
            size += len(data)

        process(all_data)

        out = '''
            length: {}
            filename: {}
            mime-type: {}
            '''.format(size, file.filename, file.content_type, data)
        return out

    @cherrypy.expose
    def index(self):
        return """
<html>
<body>
    <div>
        <form method="POST" enctype="multipart/form-data" action="/upload">
            <table>
                <tr>
                    <td>File to upload:</td>
                    <td><input type="file" name="file"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Upload"/></td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
"""


cherrypy.config.update({'server.socket_host': '0.0.0.0'})
cherrypy.config.update({'server.socket_port': 8081})
cherrypy.quickstart(Root())

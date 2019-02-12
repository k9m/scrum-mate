import os

import cherrypy

from service.ocr_service import process


class Root(object):

    @cherrypy.expose
    def upload(self, file):
        all_data = bytearray()
        while True:
            data = file.file.read(8192)
            all_data += data
            if not data:
                break

        return process(all_data)

    @cherrypy.expose
    def index(self):
        return """
<html>
<body>
    <div class="container" align="center">
            <div class="col-md-6" align="center">
                <h2 class="display-4">Scrum8 uploader</h2>
                
                <form method="POST" enctype="multipart/form-data" action="/upload">
                <div class="input-group">
                  <div class="input-group-prepend">
                    <input type="submit" class="btn btn-primary" value="Upload"/>
                  </div>
                  <div class="custom-file">
                    <input type="file" class="custom-file-input"  name="file" >
                    <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                  </div>
                </div>
                 </form>
            </div>
    </div>
    <link rel="stylesheet" href="static/bootstrap.min.css"/>
    <script src="static/bootstrap.bundle.min.js"></script>
</body>
</html>
"""


conf = {
    '/': {
        'tools.sessions.on': True,
        'tools.staticdir.root': os.path.abspath(os.getcwd())
    },
    '/static': {
        'tools.staticdir.on': True,
        'tools.staticdir.dir': 'assets'
    }
}

cherrypy.config.update({'server.socket_host': '0.0.0.0'})
cherrypy.config.update({'server.socket_port': 8081})
cherrypy.quickstart(Root(), '/', conf)

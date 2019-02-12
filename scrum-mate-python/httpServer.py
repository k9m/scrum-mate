import cherrypy
import simplejson

class Root(object):

    @cherrypy.expose
    def upload(self, file):
        cl = cherrypy.request.headers['Content-Length']
        data = file.file.read(8192)
        print(data)
        # TODO do the stuff

        # body = simplejson.loads(rawbody)
        # do_something_with(body)
        return "Blah"

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


cherrypy.config.update({'server.socket_port': 8081})
cherrypy.quickstart(Root())

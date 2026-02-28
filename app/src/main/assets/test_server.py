import http.server
import socketserver
import os
import posixpath

PORT = 4444
DIRECTORY = "app/src/main/assets"

class Handler(http.server.SimpleHTTPRequestHandler):
    def translate_path(self, path):
        # The HTML looks for /assets/style.css, so route /assets back to our local DIRECTORY
        if path.startswith("/assets/"):
            path = path.replace("/assets/", "/", 1)
        
        # We need to map the URL path to the local OS path
        return os.path.join(DIRECTORY, *path.split("/"))

with socketserver.TCPServer(("", PORT), Handler) as httpd:
    print(f"Serving {DIRECTORY} at port {PORT}")
    httpd.serve_forever()

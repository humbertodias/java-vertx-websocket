import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Server extends AbstractVerticle {

    public static void main(String... args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Server());
    }

    @Override
    public void start() {
        vertx.createHttpServer()
                .websocketHandler(ws -> {
                    if(ws.path().equals("/some-uri")){
                        ws.writeTextMessage("Server OK");
                    } else {
                        ws.handler(ws::writeBinaryMessage);
                    }

                })
                .requestHandler(req -> {
            if (req.uri().equals("/")) req.response().sendFile("ws.html");
        }).listen(8080);
    }
}

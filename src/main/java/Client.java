import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.Vertx;

public class Client extends AbstractVerticle {

    public static void main(String... args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Client());
    }

    @Override
    public void start() {
        HttpClient client = vertx.createHttpClient();

        client.websocket(8080, "localhost", "/some-uri", websocket -> {
            websocket.handler(data -> {
                System.out.println("Received data " + data.toString("UTF-8"));
                client.close();
            });
            websocket.writeBinaryMessage(Buffer.buffer("Hello world"));
        });
    }
}

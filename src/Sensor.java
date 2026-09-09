import java.io.*;
import java.net.*;
import java.util.Random;

public class Sensor {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java Sensor <nomeDoSensor>");
            return;
        }
        String nomeSensor = args[0];
        String host = "localhost";
        int porta = 9876;
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress endereco = InetAddress.getByName(host);
            Random random = new Random();
            while (true) {
                double valor = 20 + random.nextDouble() * 15;
                String mensagem = nomeSensor + ": " + String.format("%.1f", valor) + "C";
                byte[] dados = mensagem.getBytes();
                DatagramPacket pacote = new DatagramPacket(dados, dados.length, endereco, porta);
                socket.send(pacote);

                System.out.println("Enviado: " + mensagem);
                Thread.sleep(1000); 
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

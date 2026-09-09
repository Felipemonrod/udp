import java.io.*;
import java.net.*;
import java.util.Random;

public class Sensor {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java Sensor <nomeDoSensor> <tipo>");
            System.out.println("Tipos disponíveis: temperatura, umidade, pressao, vazao");
            return;
        }
        String nomeSensor = args[0];
        String tipo = args[1];
        String host = "localhost";
        int porta = 9876;
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress endereco = InetAddress.getByName(host);
            Random random = new Random();
            while (true) {
                String mensagem = nomeSensor + ": " + gerarLeitura(tipo, random);
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

    private static String gerarLeitura(String tipo, Random random) {
        switch (tipo.toLowerCase()) {
            case "umidade":
                double umidade = 40 + random.nextDouble() * 40; 
                return String.format("%.1f", umidade) + "%RH";
            case "pressao":
                double pressao = 980 + random.nextDouble() * 40; 
                return String.format("%.1f", pressao) + "hPa";
            case "vazao":
                double vazao = random.nextDouble() * 10; 
                return String.format("%.1f", vazao) + "L/min";
            case "temperatura":
            default:
                double temperatura = 20 + random.nextDouble() * 15;
                return String.format("%.1f", temperatura) + "C";
        }
    }
}

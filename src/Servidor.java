import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    public static void main(String[] args) {
        try (DatagramSocket servidor = new DatagramSocket(9876)) {
            System.out.println("Servidor UDP aguardando leituras na porta 9876...");

            byte[] buffer = new byte[1024];
            Map<String, String> ultimasLeituras = new LinkedHashMap<>();

            while (true) {
                DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
                servidor.receive(pacote);
                String mensagem = new String(pacote.getData(), 0, pacote.getLength());
                String[] partes = mensagem.split(":", 2);
                String sensor = partes[0].trim();
                String leitura = partes.length > 1 ? partes[1].trim() : mensagem;
                ultimasLeituras.put(sensor, leitura);
                System.out.println("\nÚltimas leituras:");
                for (Map.Entry<String, String> entrada : ultimasLeituras.entrySet()) {
                    System.out.println(entrada.getKey() + ": " + entrada.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

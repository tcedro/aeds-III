package src.services;

public class ProgressBar {
    public static void start(String[] args) {
        int total = 100; // O valor máximo da barra de progresso
        int width = 50; // A largura da barra de progresso em caracteres

        for (int i = 0; i <= total; i++) {
            int percent = (i * 100) / total;
            int progressBarWidth = (i * width) / total;

            // Construa a string da barra de progresso
            StringBuilder progressBar = new StringBuilder("[");
            for (int j = 0; j < width; j++) {
                if (j < progressBarWidth) {
                    progressBar.append("=");
                } else {
                    progressBar.append(" ");
                }
            }
            progressBar.append("] " + percent + "%");

            // Limpe a linha anterior
            System.out.print("\r");

            // Imprima a barra de progresso atual
            System.out.print(progressBar);

            // Aguarde um curto período de tempo para simular o progresso
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nConcluído!");
    }
}
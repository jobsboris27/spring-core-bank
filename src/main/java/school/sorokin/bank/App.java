package school.sorokin.bank;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school.sorokin.bank.config.AppConfig;
import school.sorokin.bank.console.ConsoleListener;

public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ConsoleListener listener = ctx.getBean(ConsoleListener.class);

        listener.start();
    }
}
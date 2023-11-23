package peaksoft.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * этот класс DispatcherServlet расширяет AbstractAnnotationConfigDispatcherServletInitializer и
 * обычно используется в приложении на основе Spring MVC для конфигурации DispatcherServlet,
 * который играет ключевую роль в обработке входящих HTTP-запросов.
 * <p>
 * Этот класс расширяет класс
 * Spring AbstractAnnotationConfigDispatcherServletInitializer, который является частью модуля Spring Web.
 * Этот класс предоставляет удобный способ программно настроить Servlet 3.0+ DispatcherServlet.
 */
public class DispatcherServlet extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * Этот метод возвращает классы конфигурации, которые определяют корневой контекст приложения.
     * В нашем случае он возвращает null, указывая,
     * что дополнительных классов конфигурации корневого контекста нет.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    /**
     * Этот метод возвращает классы конфигурации для собственного контекста приложения DispatcherServlet.
     * В нашем случае он возвращает массив, содержащий SpringConfig.class. Этот класс, вероятно, содержит конфигурации,
     * относящиеся к веб-части вашего приложения, такие как контроллеры, резольверы представлений и так далее.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    /**
     * Этот метод указывает сопоставление(ия) сервлета для DispatcherServlet.
     * В нашем случае он возвращает массив, содержащий "/", указывая,
     * что DispatcherServlet должен обрабатывать все запросы,
     * поступающие в приложение.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * В заключение, этот класс DispatcherServlet настраивает DispatcherServlet в приложении на основе Spring MVC,
     * указывая, где находится конфигурация сервлета и как его сопоставить с входящими запросами.
     * Если у вас есть конкретные вопросы или что-то еще, что вы хотели бы узнать о этом классе, не стесняйтесь спрашивать!
     *  */
}

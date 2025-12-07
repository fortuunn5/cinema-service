package org.example.cinemaservice;

import org.example.cinemaservice.listener.BaseDeleteModificationListener;
import org.example.cinemaservice.listener.BaseModificationListener;
import org.example.cinemaservice.listener.BaseSaveModificationListener;
import org.example.cinemaservice.listener.ListenerStage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ContextListenerUtil implements ApplicationContextAware {
    public static Map<Class, List<BaseDeleteModificationListener>> deleteListeners;
    public static Map<Class, List<BaseSaveModificationListener>> saveListeners;

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextListenerUtil.applicationContext = applicationContext;

        deleteListeners = getListenersOfType(BaseDeleteModificationListener.class).stream()
                .filter(x -> x.getStage() == ListenerStage.DELETE)
                .collect(Collectors.groupingBy(BaseDeleteModificationListener::getClass));

        saveListeners = getListenersOfType(BaseSaveModificationListener.class).stream()
                .filter(x -> x.getStage() == ListenerStage.DELETE)
                .collect(Collectors.groupingBy(BaseSaveModificationListener::getClass));
    }



    public static <T> Collection<? extends T> getListenersOfType(Class<T> clazz) {
        Map<String, T> beans = applicationContext.getBeansOfType(clazz);
        return new ArrayList<>(beans.values());
    }
}

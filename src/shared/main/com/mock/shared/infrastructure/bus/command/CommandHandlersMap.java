package com.mock.shared.infrastructure.bus.command;

import com.mock.shared.domain.Injectable;
import com.mock.shared.domain.bus.command.Command;
import com.mock.shared.domain.bus.command.UseCaseCommandHandler;
import com.mock.shared.infrastructure.google.ReflectionClasspaths;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Injectable
public final class CommandHandlersMap {
    private final HashMap<Class<? extends Command>, Class<? extends UseCaseCommandHandler>>
                                       indexedUseCaseCommandHandlers;
    private final ReflectionClasspaths classPaths;

    public CommandHandlersMap(ReflectionClasspaths classPaths) {
        this.classPaths = classPaths;
        Set<Class<? extends UseCaseCommandHandler>> classes =
                classPaths.bySubType(UseCaseCommandHandler.class);

        indexedUseCaseCommandHandlers = formatHandlers(classes);
        validateExitsHandlersForCommands(classPaths.bySubType(Command.class));

    }

    private void validateExitsHandlersForCommands(Set<Class<? extends Command>> commands) {
        Set<Class<?>> subtypes = new HashSet<>();
        for (Class<? extends Command> command : commands) {
            subtypes.clear();
            subtypes.addAll(getInheritClasses(command));
        }
    }

    private Set<Class<?>> getInheritClasses(Class<?> clazz) {
        Set<Class<?>> classes        = new HashSet<>();
        Set<Class<?>> inheritClasses = classPaths.bySubType(clazz);
        inheritClasses.forEach(inheritClazz -> {
            classes.addAll(getInheritClasses(inheritClazz));
        });

        return classes;
    }

    public Class<? extends UseCaseCommandHandler> search(Class<? extends Command> commandClass) {
        return indexedUseCaseCommandHandlers.get(commandClass);
    }

    private HashMap<Class<? extends Command>, Class<? extends UseCaseCommandHandler>> formatHandlers(
            Set<Class<? extends UseCaseCommandHandler>> commandHandlers
                                                                                                    ) {
        HashMap<Class<? extends Command>, Class<? extends UseCaseCommandHandler>> handlers = new HashMap<>();

        for (Class<? extends UseCaseCommandHandler> handler : commandHandlers) {
            ParameterizedType        paramType    = (ParameterizedType) handler.getGenericInterfaces()[0];
            Class<? extends Command> commandClass = (Class<? extends Command>) paramType.getActualTypeArguments()[0];

            handlers.put(commandClass,
                         handler);
        }

        return handlers;
    }
}

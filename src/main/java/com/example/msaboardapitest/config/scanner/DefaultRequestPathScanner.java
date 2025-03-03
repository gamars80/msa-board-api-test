package com.example.msaboardapitest.config.scanner;


import com.example.msaboardapitest.annotation.ApiFor;
import com.example.msaboardapitest.config.scanner.requestmapping.RequestMappingInClass;
import com.example.msaboardapitest.config.scanner.requestmapping.RequestMappingInMethod;
import com.example.msaboardapitest.enums.RoleType;
import lombok.Getter;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.*;

import static org.reflections.scanners.Scanners.MethodsAnnotated;
import static org.reflections.scanners.Scanners.TypesAnnotated;

@Component
public class DefaultRequestPathScanner implements RequestPathScanner {

    public Map<RoleType, Set<String>> scanRequestMethods() {
        Map<RoleType, Set<String>> result = new HashMap<>();
        for (RoleType roleType : RoleType.values()) {
            result.put(roleType, new HashSet<>());
        }

        for (AnnotatedMethod annotatedMethod : scanMethodsAnnotatedApiFor()) {
            findRequestInfoOfMethod(annotatedMethod).ifPresent(requestInfo ->
                    Arrays.stream(annotatedMethod.getRoles()).forEach(role ->
                            result.get(role).addAll(requestInfo.requestPaths)
                    ));
        }

        return result;
    }

    private List<AnnotatedMethod> scanMethodsAnnotatedApiFor() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage("com.example.msaboardapitest")
                .setScanners(TypesAnnotated, MethodsAnnotated));

        Map<String, AnnotatedMethod> map = new HashMap<>();
        reflections.getMethodsAnnotatedWith(ApiFor.class)
                .forEach(method -> {
                    String name = String.format("%s.%s", method.getDeclaringClass().getName(), method.getName());
                    map.put(name, new AnnotatedMethod(method, method.getAnnotation(ApiFor.class)));
                });

        reflections.getTypesAnnotatedWith(ApiFor.class)
                .forEach(clazz ->
                        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
                            ApiFor annotation = clazz.getAnnotation(ApiFor.class);
                            String name = String.format("%s.%s", clazz.getName(), method.getName());
                            map.putIfAbsent(name, new AnnotatedMethod(method, annotation));
                        }));

        return map.values().stream().toList();
    }

    private Optional<RequestInfo> findRequestInfoOfMethod(AnnotatedMethod target) {
        Set<String> requestPaths = new HashSet<>();
        Method method = target.getMethod();

        Optional<RequestMappingInMethod> optionalAnnotationInMethod = RequestMappingInMethod.findRequestMappingIn(method);
        if (optionalAnnotationInMethod.isEmpty()) return Optional.empty();

        RequestMappingInMethod annotationInMethod = optionalAnnotationInMethod.get();

        RequestMappingInClass.findRequestMappingIn(target.getClazz())
                .ifPresentOrElse(
                        requestMappingInClass ->
                                requestPaths.addAll(annotationInMethod.mergeRequestPathsWithClass(requestMappingInClass.getPaths())),
                        () -> requestPaths.addAll(annotationInMethod.getPaths())
                );

        return Optional.of(new RequestInfo(annotationInMethod.getMethods(), requestPaths));
    }

    @Getter
    private static class AnnotatedMethod {
        private final Method method;
        private final ApiFor annotationOfApiFor;

        public AnnotatedMethod(Method method, ApiFor annotationOfApiFor) {
            this.method = method;
            this.annotationOfApiFor = annotationOfApiFor;
        }

        public RoleType[] getRoles() {
            return annotationOfApiFor.roles();
        }

        public Class getClazz() {
            return method.getDeclaringClass();
        }
    }

    private static class RequestInfo {
        private final Set<RequestMethod> requestMethods = new HashSet<>();
        private final Set<String> requestPaths = new HashSet<>();

        public RequestInfo(Set<RequestMethod> requestMethods, Set<String> requestPaths) {
            this.requestMethods.addAll(requestMethods);
            this.requestPaths.addAll(requestPaths);
        }
    }
}

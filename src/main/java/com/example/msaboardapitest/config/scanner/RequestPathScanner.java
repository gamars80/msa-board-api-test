package com.example.msaboardapitest.config.scanner;


import com.example.msaboardapitest.enums.RoleType;

import java.util.Map;
import java.util.Set;

public interface RequestPathScanner {
    Map<RoleType, Set<String>> scanRequestMethods();
}

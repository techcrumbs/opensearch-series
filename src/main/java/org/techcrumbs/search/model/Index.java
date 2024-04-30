package org.techcrumbs.search.model;

import org.techcrumbs.Helper;

import java.util.List;
import java.util.Optional;

public record Index(
        String name,
        List<String> aliases,
        int numShards,
        int numReplicas,
        String mappingFile,
        Optional<Integer> refreshIntervalOptional) {

    public boolean hasAlias() {
        return ! Helper.isEmptyList(aliases);
    }

    public String referenceName() {
        if (hasAlias()) {
            return aliases.get(0);
        }

        return name;
    }
}

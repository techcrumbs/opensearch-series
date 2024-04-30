package org.techcrumbs.search.management.exceptions;

import org.techcrumbs.search.model.Index;

public class IndexException extends Exception {

    private Index index;

    public IndexException(Index index) {
        super();
        this.index = index;
    }

    public Index getIndex() {
        return index;
    }
}

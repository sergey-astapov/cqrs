package com.cqrs.aes.model

import com.cqrs.aes.model.ContextId
import spock.lang.Specification

class ContextIdTest extends Specification {
    def "new Context Id test"() {
        given: "a new Context Id is created"
        def id = ContextId.createNew();

        expect: "Inner id should not be null"
        id.id != null
        !id.id.empty
    }
}
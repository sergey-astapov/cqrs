package com.cqrs.aes.business.handler.event;

import com.cqrs.aes.api.event.ContextChunkProcessedEvent;
import com.cqrs.aes.api.event.ContextCompletedEvent;
import com.cqrs.aes.api.event.ContextCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;

@Slf4j
public class ContextSaga extends AbstractAnnotatedSaga {

    private boolean paid = false;
    private boolean delivered = false;
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(ContextCreatedEvent event) {
    }
}
//        // client generated identifiers (1)
//        ShippingId shipmentId = createShipmentId();
//        InvoiceId invoiceId = createInvoiceId();
//        // associate the Saga with these values, before sending the commands (2)
//        associateWith("shipmentId", shipmentId);
//        associateWith("invoiceId", invoiceId);
//        // send the commands
//        commandGateway.send(new PrepareShippingCommand(...));
//        commandGateway.send(new CreateInvoiceCommand(...));
//    }
//
//    @SagaEventHandler(associationProperty = "shipmentId")
//    public void handle(ShippingArrivedEvent event) {
//        delivered = true;
//        if (paid) {
//            end();
//            (3)
//        }
//    }
//
//    @SagaEventHandler(associationProperty = "invoiceId")
//    public void handle(InvoicePaidEvent event) {
//        paid = true;
//        if (delivered) {
//            end();
//            (4)
//        }
//    }
//}

package tech.laye.tourisme_api.payment;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tech.laye.tourisme_api.user.UserService;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final UserService userService;
    private final NotificationProducer notificationProducer;

    public Integer createPayment( PaymentRequest paymentRequest ) {
        var payment = paymentRepository.save(mapper.toPayment(paymentRequest));

        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        payment.getId(),
                        paymentRequest.orderReference(),
                        paymentRequest.amount(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.firstname(),
                        paymentRequest.lastname(),
                        paymentRequest.email()
                )
        );
        return payment.getId();
    }

    public Payment findById(Integer id) {
       return  paymentRepository.findById(id).orElseThrow(
               ()  -> new EntityNotFoundException("payment not found")
       );
    }
}

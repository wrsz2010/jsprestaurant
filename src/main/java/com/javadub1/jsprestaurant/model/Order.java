package com.javadub1.jsprestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/*
 * Składamy zamówienie w restauracji.
 *
 * Na zamówieniu jest informacja "co zamawiamy" - jedno pole z opisem zamówienia.
 * Dodajemy godzinę złożenia zamówienia, oraz godzinę dostarczenia zamówienia.
 *
 * W zamówieniu musi być podana ilość (ile osób) oraz numer stolika.
 *
 * Kwota zapłacona.
 * Kwota rachunku.
 * */
@Data // EqualsHashCode // ToString // Getter //Setter ...
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "rOrder")
public class Order implements IBaseEntity{
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    @Id
    // identity - baza przydziela identyfikator, wstawiamy rekord i sprawdzamy id ktore zostalo wstawione
    // sequence - hibernate sequence - pobierz id, przypisz do obiektu, następnie wstaw obiekt do bazy
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String description; // co zamawiamy.
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<Product> products;

    @CreationTimestamp
    private LocalDateTime timeOrdered; // moment dodania rekordu do bazy, to moment wstawienia wartości
    private LocalDateTime timeDelivered; // moment dostarczenia zamówienia.

    private int peopleCount;

    private int tableNumber;


    @Formula("(select count(*) from Product p where p.order_id = id)")
    private Integer productsCount;

//    @Formula("(select sum(o.creation_date) from Orders o where o.customer_id = id)")
    @Formula("(select sum(p.value*p.amount) from Product p where p.order_id = id)")
    private Double toPay;
    private Double paid;  // jeśli null, to jeszcze(mamy nadzieje) nie zapłacono

    public Double calculateToPay() {
        double tmpToPay = (toPay != null ? toPay : 0); // jeśli toPay != null to zwróć toPay, w przeciwnym razie 0
        double tmpPaid = (paid != null ? paid : 0); // to samo

        return tmpToPay - tmpPaid;
    }

    public String getFormattedTimeOrdered() {
        return timeOrdered == null ? "Not ordered yet" : timeOrdered.format(FORMATTER);
    }

    public String getFormattedTimeDelivered() {
        return timeDelivered == null ? "Not delivered yet" : timeDelivered.format(FORMATTER);
    }

}

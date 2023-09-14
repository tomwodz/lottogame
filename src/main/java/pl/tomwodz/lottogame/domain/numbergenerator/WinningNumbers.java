package pl.tomwodz.lottogame.domain.numbergenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class WinningNumbers {

    private String id;
    private Set<Integer> winningNumbers;
    private LocalDateTime date;

    public WinningNumbers(Set<Integer> winningNumbers, LocalDateTime date) {
        this.winningNumbers = winningNumbers;
        this.date = date;
    }
}

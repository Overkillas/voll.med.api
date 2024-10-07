package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;


public record DadosCadastroMedico(
        //bean validadation:
        @NotBlank //Nao nulo e não pode ser vazio
        String nome,
        @NotBlank @Email
        String email,
        @NotBlank @Pattern(regexp = "\\d{4,6}") //  0 \\d diz que é digito, o {4,6} diz quantos digitos
        String crm,
        @NotNull
        Especialidade especialidade,
        @NotNull @Valid // @Valid serve para checar se o outro DTO ta valido tbm
        DadosEndereco endereco) {
}

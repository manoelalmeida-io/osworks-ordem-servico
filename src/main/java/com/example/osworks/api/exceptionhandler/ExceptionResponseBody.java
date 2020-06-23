package com.example.osworks.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponseBody {

	private Integer status;
	private OffsetDateTime datahora;
	private String titulo;
	private List<Campo> campos;
	
	@Getter @Setter
	@AllArgsConstructor
	public static class Campo {
		private String nome;
		private String mensagem;
	}
}

package br.com.fiap.aquasafe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.aquasafe.dto.RegiaoDTO;
import br.com.fiap.aquasafe.model.RegiaoMonitorada;

@Service
public class RegiaoPaginacaoService {

	@Autowired
	private RegiaoCachingService cacheR;

	@Transactional(readOnly = true)
	public Page<RegiaoDTO> paginar(PageRequest req) {
		Page<RegiaoMonitorada> paginadas = cacheR.findAll(req);
		return paginadas.map(regiao -> new RegiaoDTO(regiao));
	}
}

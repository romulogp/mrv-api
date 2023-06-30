package br.com.okfx.mrv.api.repository;

import br.com.okfx.mrv.api.model.BaseLegal;
import br.com.okfx.mrv.api.repository.baseLegal.BaseLegalRepositoryQuery;
import br.com.okfx.mrv.api.repository.filter.BaseLegalFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseLegalRepository extends JpaRepository<BaseLegal, Long>, BaseLegalRepositoryQuery {}

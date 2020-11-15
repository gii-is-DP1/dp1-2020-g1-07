package org.springframework.samples.petclinic.web;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Skill;
import org.springframework.samples.petclinic.service.CasinotableService;


public class SkillFormatter implements Formatter<Skill> {
	private final CasinotableService casinotableService;
	   
    @Autowired
    public SkillFormatter(CasinotableService casinotableService) {
        this.casinotableService = casinotableService;
    }
    @Override
    public String print(Skill skill, Locale locale) {
        return skill.getName();
    }
    @Override
    public Skill parse(String text, Locale locale) throws ParseException {
        Collection<Skill> findSkillLevels = this.casinotableService.findSkills();
        for (Skill skill : findSkillLevels) {
            if (skill.getName().equals(text)) {
                return skill;
            }
        }
        throw new ParseException("type not found: " + text, 0);
    }
	

}

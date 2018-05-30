package com.dnf.reverse2.index;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dnf.model.Audience;
import com.dnf.reverse2.Index;
import com.dnf.reverse2.comparator.AssignmentComparator;
import com.dnf.reverse2.index.build.AgeBuild;
import com.dnf.reverse2.index.build.AssignBuild;
import com.dnf.reverse2.index.build.CountryBuild;
import com.dnf.reverse2.index.build.SexBuild;
import com.dnf.reverse2.model.Assignment;
import com.dnf.reverse2.model.Conjunction;

public class AssignIndex extends AbsIndex<Conjunction> {

	static AssignmentComparator assignmentComparator = new AssignmentComparator();
	
	public List<AssignBuild> assignBuilds;

	public AssignIndex() {
		assignBuilds = new LinkedList<>();
		//assignBuilds.add(new VersionBuild());
		//assignBuilds.add(new LanguageBuild());
		assignBuilds.add(new CountryBuild());
		//assignBuilds.add(new AppBuild());
		assignBuilds.add(new AgeBuild());
		assignBuilds.add(new SexBuild());
	}

	@Override
	protected Conjunction build(Audience audience,Index index) {
		int size = 0;
		List<Integer> assignments = new ArrayList<>(assignBuilds.size());
		for (AssignBuild assignBuild : assignBuilds) {
			Assignment assignment = assignBuild.analysisTerm(audience, index);
			if (assignment == null) {
				continue;
			}

			if (assignment.isBelong()) {
				size++;
			}

			Integer assignId = this.toAssignId(assignment, index);
			assignments.add(assignId);
		}

		Conjunction conjunction = new Conjunction(assignments, size);

		return conjunction;
	}

	private Integer toAssignId(Assignment assignment, Index index) {
		Map<Assignment, Integer> assignsHash = index.assignsHash;
		Integer integer = assignsHash.get(assignment);
		if(integer==null) {
			integer = assignsHash.size();
			assignsHash.put(assignment, integer);
			
			assignment.setId(integer);
			index.add(assignment);
		}
		
		return integer;
		//List<Assignment> assigns = index.assigns;
		
//		int id = Collections.binarySearch(assigns,assignment,assignmentComparator);
//		if(id>-1) {
//			System.out.println("assing-->"+id);
//			return assigns.get(id).getId();
//		}
//		for (Assignment assign : assigns) {
//			if (assign.equals(assignment)) {
//				return assign.getId();
//			}
//		}
		
		
//		assignment.setId(assigns.size());
//		assigns.add(assignment);

//		assignment.setId(index.assigns.size());
//		index.assigns.add(assignment);
		
//		Collections.sort(index.assigns, assignmentComparator);

//		return assignment.getId();
	}

}

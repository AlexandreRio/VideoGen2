package org.xtext.example.videogen.ui.handlers;

import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.xtext.example.mydsl.ui.VideoGenConsole;
import org.xtext.example.mydsl.videoGen.VideoGeneratorModel;

import videogentools.GenerateHelper;

public class GenerateHanders extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) {
		VideoGenConsole console = VideoGenConsole.getInstance();

		IWorkbenchPart workbenchPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart();
		IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput()
				.getAdapter(IFile.class);
		IProject project = file.getProject();
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		IResourceSetProvider rs1 = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(uri)
				.get(IResourceSetProvider.class);
		ResourceSet rs = rs1.get(project);
		Resource r = rs.getResource(uri, true);

		VideoGeneratorModel model = (VideoGeneratorModel) r.getContents().get(0);
		console.printMessageln(GenerateHelper.generate(model).stream().collect(Collectors.joining(",")));
		console.printMessageln("Auteur " + model.getInformation().getAuthorName());

		return null;
	}
}

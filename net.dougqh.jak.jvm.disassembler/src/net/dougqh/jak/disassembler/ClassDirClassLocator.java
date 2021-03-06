package net.dougqh.jak.disassembler;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import net.dougqh.aggregator.InputProvider;
import net.dougqh.aggregator.InputScheduler;

final class ClassDirClassLocator implements ClassLocator {
	private static final FileFilter DIR_FILTER = new FileFilter() {
		@Override
		public final boolean accept( final File file ) {
			return file.isDirectory();
		}
	};
	
	private static final FilenameFilter CLASS_FILTER = new FilenameFilter() {
		@Override
		public final boolean accept( final File dir, final String name ) {
			return name.endsWith( ".class" );
		}
	};
	
	private final File dir;
	
	ClassDirClassLocator( final File dir ) {
		this.dir = dir;
	}
	
	@Override
	public final InputStream load( final String className ) throws IOException {
		File classFile = new File( this.dir, className.replace( '.', '/' ) + ".class" );
		return new FileInputStream( classFile );
	}
	
	@Override
	public final void enumerate(final InputScheduler<? super ClassBlock> scheduler)
		throws InterruptedException
	{
		scheduler.schedule(new DirInputProvider(this.dir));
	}
	
	private static final class DirInputProvider implements InputProvider<ClassBlock> {
		private final File dir;
		
		public DirInputProvider(final File dir) {
			this.dir = dir;
		}
		
		@Override
		public final void run(final InputScheduler<? super ClassBlock> scheduler) throws Exception {
			File[] subDirs = this.dir.listFiles(DIR_FILTER);
			
			if ( subDirs != null ) {
				for ( File subDir: subDirs ) {
					scheduler.schedule(new DirInputProvider(subDir));
				}
			}
			
			File[] classFiles = this.dir.listFiles(CLASS_FILTER);
			
			if ( classFiles != null && classFiles.length != 0 ) {
				scheduler.offer(new FilesClassBlock(classFiles));
			}
		}
	}
	
	private static final class FilesClassBlock implements ClassBlock {
		private final File[] classFiles;
		
		public FilesClassBlock(final File[] classFiles) {
			this.classFiles = classFiles;
		}
		
		@Override
		public final void process(final ClassProcessor processor) throws IOException {
			for ( File classFile: this.classFiles ) {
				FileInputStream in = new FileInputStream(classFile);
				try {
					processor.process(in);
				} finally {
					in.close();
				}
			}
		}
	}
}

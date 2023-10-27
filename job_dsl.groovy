folder("Tools") {
    description("Folder for miscellaneous tools.")
    job("clone-repository") {
        displayName("Clone Repository")
        description("Clone a Git repository")
        parameters {
            stringParam("GIT_REPOSITORY_URL", "", "Git URL of the repository to clone")
        }
        steps {
            preBuildCleanup()
            shell('git clone $GIT_REPOSITORY_URL')
        }
    }
    job("SEED") {
        displayName("Seed Jobs")
        description("Create jobs based on a template")
        parameters {
            stringParam("GITHUB_NAME", "", "GitHub repository owner/repo_name (e.g.: \"EpitechIT31000/chocolatine\")")
            stringParam("DISPLAY_NAME", "", "Display name for the job")
        }
        steps {
            preBuildCleanup()
            
            jobDsl(scriptText: """
                freeStyleJob("\$DISPLAY_NAME") {
                    description("A job created by the Seed Jobs job")
                    scm {
                        git("https://github.com/\$GITHUB_NAME.git")
                    }
                    steps {
                        preBuildCleanup()
                        
                        shell("make fclean")
                        shell("make")
                        shell("make tests_run")
                        shell("make clean")
                    }
                }
            """)
        }
    }
    def preBuildCleanup() {
        cleanWs()
    }
}
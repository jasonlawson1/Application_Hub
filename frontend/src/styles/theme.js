import { createTheme } from '@mui/material/styles';

const theme = createTheme({
    spacing: 8,
    palette: {
        primary: {
            main: '#5e1db9',
        },
        secondary: {
            main: '#9128d8'
        },
        background: {
            default: '#928aff',
        },
    },
    components: {
        MuiButton: {
            styleOverrides: {
                root: {
                    margin: 4
                }
            }
        },
        MuiPaper: {
            styleOverrides: {
                root: {
                    margin: 4,
                    padding: 8,
                    borderRadius: 12,
                }
            }
        },
        MuiCard: {
            styleOverrides: {
                root: {
                    margin: 4,
                    padding: 8,
                    borderRadius: 12,
                    backgroundColor: 'white'
                }
            }
        },
    }
});

export default theme;